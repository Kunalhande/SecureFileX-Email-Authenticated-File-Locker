package com.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import com.dao.userdao;
import com.model.User;
import com.service.GenerateOTP;
import com.service.SendOTPService;
import com.service.UserService;

public class Welcome {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner sc = new Scanner(System.in);

    public void Welcome() {

        int choice = 0;

        System.out.println("wlcm to app");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to SignUp");
        System.out.println("Press 0 to exit");

        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        switch (choice) {
            case 1:
                login();
                break;

            case 2:
                SignUp();
                break;

            case 0:
                System.exit(0);
                break;

            default:
                System.out.println("Invalid choice");
        }
    }

    public void login() {

        System.out.println("Enter Email");
        String email = sc.nextLine();

        try {
            if (userdao.isExist(email)) {

                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);

                System.out.println("Enter the otp");
                String otp = sc.nextLine();

                if (otp.equals(genOTP)) {
                    new UserView(email).home();
                } else {
                    System.out.println("wrong otp");
                }

            } else {
                System.out.println("User not found");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void SignUp() {

        System.out.println("Enter name");
        String name = sc.nextLine();

        System.out.println("Enter email");
        String email = sc.nextLine();

        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);

        System.out.println("Enter the otp");
        String otp = sc.nextLine();

        if (otp.equals(genOTP)) {

            User user = new User(name, email);

            Integer response = UserService.saveUser(user);

            if (response == null) {
                System.out.println("Registration failed");
                return;
            }

            switch (response) {

                case 1:
                    System.out.println("User registered");
                    break;

                case 0:
                    System.out.println("User already exists");
                    break;

                default:
                    System.out.println("Registration failed");
            }

        } else {
            System.out.println("wrong OTP");
        }
    }
}