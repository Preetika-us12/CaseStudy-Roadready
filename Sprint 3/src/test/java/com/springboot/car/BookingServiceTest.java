package com.springboot.car;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Customer;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.service.BookingService;  // <-- updated import

@SpringBootTest
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;  // <-- updated here

    @Mock
    private CustomerRepository customerRepository;
@Mock
    private BookingRepository bookingRepository;

    @Mock
    private CarRepository carRepository;
    private Customer customer;
    private Car car;
    private Booking booking;

    @BeforeEach
    public void init() {
        customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setEmail("john@gmail.com");
        System.out.println("customer created at " + customer);
        
        car = new Car();
         car.setId(101);
        car.setBrand("Toyota");
        car.setModel("Camry");
        System.out.println("car created at " + car);
        
        booking = new Booking();
        booking.setId(501);
         booking.setPickupDate(LocalDate.now().plusDays(1));   
        booking.setDropoffDate(LocalDate.now().plusDays(5));
        booking.setCar(car);
        booking.setCustomer(customer);
        System.out.println("booking created at " + booking);
    }

    @Test
    public void getCarByCustomerIdTest() {
        List<Car> expected = List.of(car);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(bookingRepository.getCarByCustomerId(1)).thenReturn(expected);

        List<Car> actual = bookingService.getCarByCustomerId(1);  // <-- call BookingService
        assertEquals(expected, actual);
    }

    @Test
    public void addBookingTest() {
        Booking b = new Booking();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(carRepository.findById(101)).thenReturn(Optional.of(car));
        when(bookingRepository.save(b)).thenReturn(booking);

        assertEquals(booking, bookingService.addBooking(1, 101, b));  

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> bookingService.addBooking(99, 101, b));
       
        assertEquals("Customer ID Invalid".toLowerCase(), e.getMessage().toLowerCase());

        e = assertThrows(ResourceNotFoundException.class,
                () -> bookingService.addBooking(1, 999, b));
        assertEquals("Car ID Invalid".toLowerCase(), e.getMessage().toLowerCase());
    }

    @AfterEach
    public void afterTest() {
        customer = null;
        car = null;
        booking = null;
        System.out.println("Test objects released...");
    }
}
