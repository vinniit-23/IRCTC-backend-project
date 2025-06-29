package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class UserBookingService {
    Scanner scan = new Scanner(System.in);
    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String User_path = "app/src/main/java/org/example/localDb/users.json";

    public List<User> loadUsers() throws Exception{
            File usersFile = new File(User_path);
           return objectMapper.readValue(usersFile, new TypeReference<List<User>>() {
            });
    }

      public UserBookingService()throws Exception{
                 loadUsers();
      }

    public UserBookingService(User user) throws Exception{
        this.user= user;
            loadUsers();
    }
   public Boolean loginUser(){
        Optional foundUser = userList.stream()
                .filter(u -> u.getName().equals(user.getName()) &&
                             UserServiceUtil.checkPassword(user.getPassword(), u.getHashedPassword()).equals("Valid Password"))
                .findFirst();
        return foundUser.isPresent();
   }
   public Boolean signUp(User user){
        try{
          userList.add(user);
          saveUsersToFile();
          return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
   }
          public void saveUsersToFile() {
              try {
                  objectMapper.writeValue(new File(User_path), userList);
              } catch (IOException e) {
                  System.out.println("Error saving users to file: " + e.getMessage());
              }
          }
          public void showBooking(){
        user.printTicketsBooked();
          }

    public Boolean cancelBooking(String ticketId) {
        System.out.println("Booked tickets");
        showBooking();
        Optional<Ticket> ticketToCancel = user.getTicketsBooked().stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst();
        if (ticketToCancel.isPresent()) {
            user.getTicketsBooked().remove(ticketToCancel.get());
            saveUsersToFile();
            return Boolean.TRUE;
        } else {
            System.out.println("Ticket not found.");
            return Boolean.FALSE;
        }
    }
    public List<Train> getTrains(String source,String destination) {
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source,destination);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
