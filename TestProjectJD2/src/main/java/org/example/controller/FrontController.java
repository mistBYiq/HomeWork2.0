package org.example.controller;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.example.controller.commands.Commands;
import org.example.domain.Car;
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
import java.nio.charset.Charset;
import java.util.Collections;

public class FrontController extends HttpServlet {

    public static final CarRepository carRepository = new CarRepositoryImpl();

    public FrontController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processGetRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processPostRequest(req, resp);
    }

//    private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/hello");
//        if (dispatcher != null) {
//            System.out.println("Forward will be done!");
//
//            req.setAttribute("users", userRepository.findAll().stream().map(User::getName).collect(Collectors.joining(",")));
//           // req.setAttribute("carModel", carRepository.findAll().stream().map(Car::getModel).collect(Collectors.joining(",")));
//
//            dispatcher.forward(req, resp);
//        }
//    }

    private void processGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Commands commandName = Commands.findByCommandName(req.getParameter("command"));
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/hello");
            if (dispatcher != null) {
                resolveGetRequestCommands(req, commandName);
                dispatcher.forward(req, resp);
            }
        } catch (Exception e) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/error");
            if (dispatcher != null) {
                req.setAttribute("trace", e.getMessage());
                dispatcher.forward(req, resp);
            }
        }
    }

    private void resolveGetRequestCommands(HttpServletRequest req, Commands commandName) {

        switch (commandName) {
            case FIND_ALL:
                String page = req.getParameter("page");
                String limit = req.getParameter("limit");
                req.setAttribute("cars", carRepository.findAll());
                break;

            case FIND_BY_ID:
                String id = req.getParameter("id");
                long carId = Long.parseLong(id);
                req.setAttribute("cars", Collections.singletonList(carRepository.findById(carId)));
                req.setAttribute("singleCar", carRepository.findById(carId));
                break;

            default:
                break;
        }
    }

    private void processPostRequest(HttpServletRequest req, HttpServletResponse resp) {
        Commands commandName = Commands.findByCommandName(req.getParameter("command"));
        try {
            switch (commandName) {
                case CREATE:
                    String body = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
                    Car car = new Gson().fromJson(body, Car.class);
                    req.setAttribute("cars", Collections.singletonList(carRepository.save(car)));
                    break;

                case UPDATE:
                    String updateBody = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
                    Car updateCar = new Gson().fromJson(updateBody, Car.class);
                    req.setAttribute("cars", Collections.singletonList(carRepository.save(updateCar)));
                    break;

                case DELETE:
                    String id = req.getParameter("id");
                    long carId = Long.parseLong(id);
                    carRepository.delete(carRepository.findById(carId));
                    req.setAttribute("cars", carRepository.findAll());
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
