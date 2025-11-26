package com.example.courseprifs.consoleCourseWork;

import com.example.courseprifs.model.User;

import java.util.Scanner;

public class MenuControl {
    public static void generateUserMenu(Scanner scanner, Wolt wolt) {
        var cmd = 0;
        while (cmd != 6) {
            System.out.println("""
                    Choose and option:
                    1 - create
                    2 - view all users
                    3 - view user
                    4 - update user
                    5 - delete user
                    3 - update
                    6 - return to main menu
                    """);
            var userInput = "";
            cmd = scanner.nextInt();
            scanner.nextLine();

            switch (cmd) {
                case 1:
                    System.out.println("Enter User data (User class):username;password;name;surname;phoneNum;address; licence; bdate;vehicle");
                    var input = scanner.nextLine();
                    String[] info = input.split(";");
                    User user = new User(info[0], info[1], info[2], info[3], info[4]);
                    wolt.getAllSystemUsers().add(user);
                    //Driver driver = new Driver(info[0], info[1], info[2], info[3], info[4], info[5], info[6], LocalDate.parse(info[7]), VehicleType.valueOf(info[8]));
                    System.out.println(info[0]);
                    //Utils.writeUserToFile(user);
                    break;

                case 2:
                    //optimized for loop
                    for(User u: wolt.getAllSystemUsers()){
                        System.out.println(u);
                    }
                    break;

                case 3:
                    System.out.println("enter login: ");
                    userInput = scanner.nextLine();
                    for(User u : wolt.getAllSystemUsers()){
                        if(u.getLogin().equals(userInput)){
                            System.out.println(u);
                        }
                    }
                    break;

                case 4:
                    System.out.println("enter login: ");
                    userInput = scanner.nextLine();
                    for(User u : wolt.getAllSystemUsers()){
                        if(u.getLogin().equals(userInput)){
                            System.out.println("Enter new data for: name:surname");
                            String[] infoForUpdate = scanner.nextLine().split(";");
                            u.setName(infoForUpdate[0]);
                            u.setSurname(infoForUpdate[1]);
                        }
                    }
                    break;

                    case 5:
                        System.out.println("enter login: ");
                        userInput = scanner.nextLine();
                        for(User u : wolt.getAllSystemUsers()){
                            if(u.getLogin().equals(userInput)){
                                wolt.getAllSystemUsers().remove(u);
                            }
                        }
                        break;


                default:
                    System.out.println();
            }
        }
    }
}
