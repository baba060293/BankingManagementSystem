package com.microservice.accounts.controller;

import com.microservice.accounts.dto.CustomerDetailsDto;
import com.microservice.accounts.dto.ErrorResponseDto;
import com.microservice.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/api", produces = {MediaType.APPLICATION_JSON})
@Tag(
        name="Rest API for customer of Microservice Bank",
        description = "Rest API for Microservice Bank to fetch Customer Details"
)
@Validated
public class CustomerController {

    private ICustomersService iCustomersService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(ICustomersService iCustomersService) {
        this.iCustomersService = iCustomersService;
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("bank-correlation-id") String correlationId,
                                                                   @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                   String mobileNumber) {
        logger.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);

    }

}
