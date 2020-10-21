package org.example.controller;

import org.example.domain.Car;
import org.example.domain.User;
import org.example.repository.CarRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.CarRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class FrontController extends HttpServlet {

    public static final UserRepository userRepository = new UserRepositoryImpl();
    public static final CarRepository carRepository = new CarRepositoryImpl();

    public FrontController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/hello");
        if (dispatcher != null) {
            System.out.println("Forward will be done!");

            req.setAttribute("users", userRepository.findAll().stream().map(User::getName).collect(Collectors.joining(",")));
           // req.setAttribute("carModel", carRepository.findAll().stream().map(Car::getModel).collect(Collectors.joining(",")));

            dispatcher.forward(req, resp);
        }
    }
}
