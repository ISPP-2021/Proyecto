package com.stalion73.web;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.Optional;

import javax.validation.Valid;

import com.stalion73.service.BookingService;
import com.stalion73.service.BusinessService;
import com.stalion73.service.ServiseService;
import com.stalion73.service.SupplierService;
import com.stalion73.service.OptionService;
import com.stalion73.model.Booking;
import com.stalion73.model.Business;
import com.stalion73.model.Servise;
import com.stalion73.model.SubscriptionType;
import com.stalion73.model.Option;
import com.stalion73.model.Supplier;

import org.springframework.http.HttpHeaders;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/business")
@CrossOrigin(origins = "*")
public class BusinessController {

    @Autowired
    private final BusinessService businessService;

    @Autowired
    private final SupplierService supplierService;

    @Autowired
    private final ServiseService serviseService;

    @Autowired
    private final BookingService bookingService;

    @Autowired
    private final OptionService optionService;

    private final static HttpHeaders headers = new HttpHeaders();

    public BusinessController(BusinessService businessService, SupplierService supplierService
    , ServiseService serviseService, BookingService bookingService, OptionService optionService) {
        this.businessService = businessService;
        this.supplierService = supplierService;
        this.serviseService = serviseService;
        this.bookingService = bookingService;
        this.optionService = optionService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> all() {
        Collection<Business> businesses = this.businessService.findAll();
        if (businesses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(headers).body(businesses);
        } else {
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(businesses);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> one(@PathVariable("id") Integer id) {
        Optional<Business> business = this.businessService.findById(id);
        if (!business.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE).headers(headers)
                    .body(Problem.create()
                            .withTitle("Negocio incorrecto")
                            .withDetail("El negocio no existe."));
        } else {
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(business.get());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody Business business,
            BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        if (bindingResult.hasErrors() || (business == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(Problem.create()
                    .withTitle("Error de validación")
                    .withDetail("El negocio no se ha podido validar correctamente."));
        } else {
            String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Supplier supplier = this.supplierService.findSupplierByUsername(username).get();
            
            if(supplier.getSubscription()==SubscriptionType.PREMIUM){

                supplier.addBusiness(business);
                business.setSupplier(supplier);
                Set<Servise> servises = business.getServices();
                Option option = business.getOption();
                //this.supplierService.save(supplier);
                this.optionService.save(option);
                this.businessService.save(business);
                servises.stream()
                .map(servise -> {
                    servise.setBussiness(business);
                    return servise;
                })
                .forEach(x -> this.serviseService.save(x));
                this.supplierService.save(supplier);
                headers.setLocation(ucBuilder.path("/business").buildAndExpand(business.getId()).toUri());
                return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(business);
            }else {

                if(supplier.getBusiness().size()>=1){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers)
                            .body(Problem.create()
                                    .withTitle("Error de negocios permitidos")
                                .withDetail("Los suscriptores gratuitos no pueden crear más de un negocio."));
                }else {
                    supplier.addBusiness(business);
                    business.setSupplier(supplier);
                    Set<Servise> servises = business.getServices();
                    Option option = business.getOption();
                    //this.supplierService.save(supplier);
                    this.optionService.save(option);
                    this.businessService.save(business);
                    servises.stream()
                    .map(servise -> {
                        servise.setBussiness(business);
                        return servise;
                    })
                    .forEach(x -> this.serviseService.save(x));
                    this.supplierService.save(supplier);
                    headers.setLocation(ucBuilder.path("/business").buildAndExpand(business.getId()).toUri());
                    return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(business);
                }

            }
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody @Valid Business newBusiness,
            BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        if (bindingResult.hasErrors() || (newBusiness == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(Problem.create()
                    .withTitle("Error de validación")
                    .withDetail("El negocio no se ha podido validar correctamente."));
        } else if (!this.businessService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE).headers(headers)
                    .body(Problem.create()
                            .withTitle("Negocio incorrecto")
                            .withDetail("El negocio no existe."));
        } else {
            // business(name, address, businessType, automatedAccept, Supplier, Servises)
            Business updatedBusiness = this.businessService.findById(id).map(business -> {
                this.businessService.update(id, newBusiness);
                Supplier supplier;
                if (newBusiness.getSupplier() == null) {
                    supplier = business.getSupplier();
                } else {
                    this.supplierService.update(business.getId(), newBusiness.getSupplier());
                    supplier = this.supplierService.findById(business.getId()).get();
                }
                business.setSupplier(supplier);
                this.businessService.save(business);
                return business;
            }).orElseGet(() -> {
                return null;
            });
            return new ResponseEntity<Business>(updatedBusiness, headers, HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}/addition", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> addServise(@PathVariable("id") Integer id, @RequestBody @Valid Servise servise,
            BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (servise == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Servise>(headers, HttpStatus.BAD_REQUEST);
        }
        Business business = this.businessService.findById(id).get();
        servise.setBussiness(business);
        this.serviseService.save(servise);
        business.addServise(servise);
        this.businessService.save(business);
        return new ResponseEntity<Servise>(servise, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}/additions", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> addServises(@PathVariable("id") Integer id, @RequestBody @Valid Set<Servise> servises,
            BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (servises == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Servise>(headers, HttpStatus.BAD_REQUEST);
        }
        Business business = this.businessService.findById(id).get();

        servises.stream()
                    .map(servise -> {
                        servise.setBussiness(business);
                        return servise;
                    })
                    .forEach(x -> this.serviseService.save(x));
        business.addServises(servises);
        this.businessService.save(business);
        return new ResponseEntity<Set<Servise>>(servises, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<Business> business = this.businessService.findById(id);
        if (business.isPresent()) {
            this.businessService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE).headers(headers)
                    .body(Problem.create()
                            .withTitle("Negocio incorrecto")
                            .withDetail("El negocio no existe."));
        }
    }

    @RequestMapping(value = "/booking/{idBooking}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> businessByBooking(@PathVariable("idBooking") Integer id) {
        Optional<Booking> booking = this.bookingService.findById(id);
        if (!booking.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE).headers(headers)
                    .body(Problem.create()
                            .withTitle("Negocio incorrecto")
                            .withDetail("El negocio no existe."));
        } else {
            Servise servise = booking.get().getServise();
            Business business = servise.getBusiness();
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(business);
        }
    }

}
