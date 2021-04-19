package com.stalion73.model.modelAssembler;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.stalion73.web.ConsumerController;
import com.stalion73.model.Consumer;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ConsumerModelAssembler implements RepresentationModelAssembler<Consumer, EntityModel<Consumer>> {

  @Override
  public EntityModel<Consumer> toModel(Consumer consumer) {
    // Unconditional links to single-item resource and aggregate root
    EntityModel<Consumer> consumerModel = EntityModel.of(consumer,
        linkTo(methodOn(ConsumerController.class).one(consumer.getId())).withSelfRel(),
        linkTo(methodOn(ConsumerController.class).all()).withRel("/consumers"));

    return consumerModel;
  }
}
