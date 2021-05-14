package com.stalion73.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import javax.validation.Valid;

import com.stalion73.service.BookingService;
import com.stalion73.service.ConsumerService;
import com.stalion73.service.ServiseService;
import com.stalion73.service.SupplierService;
import com.stalion73.service.OptionService;
import com.stalion73.model.Booking;
import com.stalion73.model.Consumer;
import com.stalion73.model.Business;
import com.stalion73.model.Supplier;
import com.stalion73.model.Servise;
import com.stalion73.model.Status;
import com.stalion73.model.Option;
import com.stalion73.model.modelAssembler.BookingModelAssembler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {
    
    @Autowired
    private final BookingService bookingService;

    @Autowired
    private final ServiseService serviseService;
    
    @Autowired
    private final ConsumerService consumerService;

    @Autowired
    private final SupplierService supplierService;

    @Autowired
    private final OptionService optionService;

    @Autowired
    private final BookingModelAssembler assembler;

    private final static HttpHeaders headers = new HttpHeaders();

    public BookingController(BookingService bookingService,ConsumerService consumerService,
                        ServiseService serviseService, SupplierService supplierService,
                         OptionService optionService, BookingModelAssembler assembler){
        this.bookingService = bookingService;
        this.serviseService = serviseService;
        this.consumerService = consumerService;
        this.supplierService = supplierService;
        this.optionService = optionService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        List<EntityModel<Booking>> bookings = this.bookingService.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());
        if(bookings.isEmpty()){
            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .headers(headers)
                .body(bookings);
        }else{
            return ResponseEntity
                .status(HttpStatus.OK) 
                .headers(headers) 
                .body(bookings);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable("id") Integer id) {
        Optional<Booking> booking = bookingService.findById((id));
        if(booking.isPresent()){
            return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(assembler.toModel(booking.get()));
        }else{
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Reserva incorrecta")
                        .withDetail("La reserva no existe."));
        }
        
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody Booking booking,BindingResult bindingResult,
                @PathVariable("id")Integer id) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        if(authority.equals("user")){
            if (bindingResult.hasErrors() || (booking == null)) {
                errors.addAllErrors(bindingResult);
                headers.add("errors", errors.toJSON());
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(headers)
                    .body(Problem.create()
                            .withTitle("Error de validación")
                            .withDetail("La reserva no se ha podido validar correctamente."));
            }else{

                String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Consumer consumer = this.consumerService.findConsumerByUsername(username).get();
                booking.setConsumer(consumer);
                Optional<Servise> s = this.serviseService.findById(id);
                if(s.isPresent()){

                    
                    Servise servise = s.get();
                    Business business = servise.getBusiness();
                    LocalTime bookTime = LocalTime.parse(booking.getBookDate().toString().substring(11,16)+":00");
                    if(bookTime.isAfter(business.getOpenTime()) && 
                            bookTime.isBefore(business.getCloseTime()) || bookTime.equals(business.getOpenTime())){

                                Option options = servise.getBusiness().getOption();
                                if(options.isAutomatedAccept()){
                                    Integer gas = options.getGas();
                                    if(gas > 0){
                                        Status status = Status.COMPLETED;
                                        options.subGasUnit();
                                        booking.setStatus(status);
                                        booking.setConsumer(consumer);
                                        booking.setService(servise);
               
                                        Calendar calendar;
                                        Date emisionDate;
                                        calendar = Calendar.getInstance();
                                        emisionDate = calendar.getTime();
                                        booking.setEmisionDate(emisionDate);
                                        booking.setService(servise);
                                        this.bookingService.save(booking);
                                        this.serviseService.save(servise);
                                        this.consumerService.save(consumer);
                                        this.optionService.save(options);
            
                                        return new ResponseEntity<Booking>(booking, headers, HttpStatus.OK);
                                    }
                                }
                                Status initState = Status.IN_PROGRESS;
                                booking.setStatus(initState);
                                booking.setConsumer(consumer);
                                booking.setService(servise);
            
                                Calendar calendar;
                                Date emisionDate;
                                calendar = Calendar.getInstance();
                                emisionDate = calendar.getTime();
                                booking.setEmisionDate(emisionDate);
                                booking.setService(servise);
                                this.bookingService.save(booking);
                                this.serviseService.save(servise);
                                this.consumerService.save(consumer);
            
                                return new ResponseEntity<Booking>(booking, headers, HttpStatus.OK);
                    }else {
                        return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST) 
                            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                            .headers(headers)
                            .body(Problem.create()
                                .withTitle("Hora invalida")
                                .withDetail("No se puede reservar a esa hora en este negocio porque está cerrado a esa hora."));
                    }
                }
                return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("No existe")
                    .withDetail("El servicio solicitado no existe."));
            }
        }
        return ResponseEntity
        .status(HttpStatus.FORBIDDEN) 
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
        .headers(headers)
        .body(Problem.create()
                .withTitle("Acceso restringido")
                .withDetail("No tienes acceso a este contenido."));

           
    }

    @RequestMapping(value = "/{id_servise}/{id_consumer}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createFor(@Valid @RequestBody Booking booking, BindingResult bindingResult, 
                @PathVariable("id_servise")Integer id_servise, @PathVariable("id_consumer") Integer id_consumer) {
        
        BindingErrorsResponse errors = new BindingErrorsResponse();
        if (bindingResult.hasErrors() || (booking == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Error de validación")
                        .withDetail("La reserva no se ha podido validar correctamente."));
        }
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        if(authority.equals("owner")){
            Supplier supplier = this.supplierService
            .findSupplierByUsername((String)SecurityContextHolder.getContext()
                                                .getAuthentication().getPrincipal()).get();
            Optional<Business> business = supplier.getBusiness().stream()
                                    .filter(x -> x.getServices().stream()
                                                    .filter(y -> y.getId().equals(id_servise))
                                                    .findAny().isPresent())
                                    .findAny();
            if(business.isPresent()){
                Optional<Consumer> c = this.consumerService.findById(id_consumer);
                if(c.isPresent()){
                    Consumer consumer = c.get();
                    Servise servise = this.serviseService.findById(id_servise).get();
                    Status initState = Status.IN_PROGRESS;
                    booking.setStatus(initState);
                    booking.setConsumer(consumer);
                    booking.setService(servise);

                    Calendar calendar;
                    Date emisionDate;
                    calendar = Calendar.getInstance();
                    emisionDate = calendar.getTime();
                    booking.setEmisionDate(emisionDate);
                    
                    servise.addBooking(booking);
                    consumer.addBooking(booking);
                    this.bookingService.save(booking);
                    this.serviseService.save(servise);
                    this.consumerService.save(consumer);

                    return new ResponseEntity<Booking>(booking, headers, HttpStatus.OK);
                }
                return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                        .withTitle("No existe")
                        .withDetail("El cliente solicitado no existe."));
            }
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Servicio innaccesible")
                    .withDetail("Este servicio no está en la lista de servicios disponibles."));
            
        }
        return ResponseEntity
        .status(HttpStatus.FORBIDDEN) 
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
        .headers(headers)
        .body(Problem.create()
                .withTitle("Error de validación")
                .withDetail("La reserva no se ha podido validar correctamente."));

        
    }
    

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, 
                                            @RequestBody @Valid Booking newBooking, 
                                            BindingResult bindingResult){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        if(bindingResult.hasErrors() || (newBooking == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Error de validación")
                        .withDetail("La reserva no se ha podido validar correctamente."));
        }else if(!this.bookingService.findById(id).isPresent()){
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Reserva incorrecta")
                        .withDetail("La reserva no existe."));
        }else{
            this.bookingService.update(id, newBooking);
            return new ResponseEntity<Booking>(newBooking, headers, HttpStatus.NO_CONTENT);
        }
    }


    // negative flow haven't been considered -> addition
    // security context -> let interact only with the bookings of the executor actor
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Integer id) {
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        if(authority.equals("user")){
            Consumer consumer = this.consumerService
            .findConsumerByUsername((String)SecurityContextHolder.getContext()
                                                .getAuthentication().getPrincipal()).get();
            Optional<Booking> b = this.bookingService.findById(id);
            if(b.isPresent()){
                Booking booking = b.get();
                Servise servise = booking.getServise();
                if(booking.getConsumer().getId().equals(consumer.getId())){
                    Status state = booking.getStatus();
                    if(state == Status.IN_PROGRESS){
                        booking.setStatus(Status.CANCELLED);
                        consumer.addBooking(booking);
                        servise.addBooking(booking);
                        booking.setConsumer(consumer);
                        booking.setService(servise);
                        this.bookingService.save(booking);
                        this.serviseService.save(servise);
                        this.consumerService.save(consumer);
                        return ResponseEntity
                            .status(HttpStatus.OK)
                            .headers(headers)
                            .body(assembler.toModel(booking));
                    }else{
                        return ResponseEntity
                        .status(HttpStatus.METHOD_NOT_ALLOWED) 
                        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                        .headers(headers)
                        .body(Problem.create()
                                .withTitle("Cancelación no permitida")
                                .withDetail("No puedes cancelar la reserva por que está en el estatus " + booking.getStatus()));
                    }
                }
                return ResponseEntity
                .status(HttpStatus.FORBIDDEN) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Servicio innaccesible")
                        .withDetail("Este servicio no está en la lista de servicios disponibles."));

            }
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                        .withTitle("No existe")
                        .withDetail("La petición de reserva solicitada no existe."));
        }
        if(authority.equals("owner")){
            Supplier supplier = this.supplierService
                    .findSupplierByUsername((String)SecurityContextHolder.getContext()
                                                .getAuthentication().getPrincipal()).get();
            Optional<Booking> b = this.supplierService.findBookingOnSupplier(supplier, id);
            if(b.isPresent()){
                Booking booking = b.get();
                Status state = booking.getStatus();
                if(state == Status.IN_PROGRESS){
                    Consumer consumer = booking.getConsumer();
                    Servise servise = booking.getServise();
                    booking.setStatus(Status.REJECTED);
                    consumer.addBooking(booking);
                    servise.addBooking(booking);
                    booking.setConsumer(consumer);
                    booking.setService(servise);
                    this.bookingService.save(booking);
                    this.serviseService.save(servise);
                    this.consumerService.save(consumer);
                    return ResponseEntity
                        .status(HttpStatus.OK)
                        .headers(headers)
                        .body(assembler.toModel(booking));
                }else{
                    return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED) 
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                    .headers(headers)
                    .body(Problem.create()
                            .withTitle("Cancelación no permitida")
                            .withDetail("No puedes cancelar la reserva por que está en el estatus " + booking.getStatus()));
                }
            }else{
                return ResponseEntity
                .status(HttpStatus.FORBIDDEN) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Servicio innaccesible")
                        .withDetail("Este servicio no está en la lista de servicios disponibles."));
            }
                                    
                                                                    
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Acceso restringido")
                        .withDetail("No tienes acceso a este contenido."));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Integer id) {
        Booking booking = this.bookingService.findById(id).get();        
        if (booking.getStatus() == Status.IN_PROGRESS) {
            booking.setStatus(Status.COMPLETED);
            this.bookingService.save(booking);
            return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(assembler.toModel(booking));
        }else if(booking.getStatus() == Status.COMPLETED){
            return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                        .withTitle("Reserva no permitida")
                        .withDetail("No puedes completar la reserva por que está en el estatus " + booking.getStatus()));
        }else{
            return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Reserva no permitida")
                    .withDetail("No puedes completar la reserva por que está en el estatus " + booking.getStatus()));
        }

    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Booking> booking = this.bookingService.findById(id);
        if(booking.isPresent()){
            this.bookingService.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .headers(headers)
                    .body(Problem.create()
                            .withTitle("Reserva incorrecta")
                            .withDetail("La reserva no existe."));
        }
    }

}
