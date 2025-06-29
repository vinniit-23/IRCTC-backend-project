package org.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {
                     private String name;
                     private String password;
                     @JsonProperty("hashed_password")
                     private String hashedPassword;;
                     private List<Ticket> ticketsBooked;
                     private String userID;

                        public User(String name, String password, String hashedPassword, List<Ticket> ticketsBooked, String userID) {
                            this.name = name;
                            this.password = password;
                            this.hashedPassword = hashedPassword;
                            this.ticketsBooked = ticketsBooked;
                            this.userID = userID;
                        }

                        public User() {
                        }

                        public String getName(){
                            return name;
                        }
                        public String getPassword() {
                            return password;
                        }
                        public String getHashedPassword() {
                            return hashedPassword;
                        }
                        public List<Ticket> getTicketsBooked() {
                            return ticketsBooked;
                        }
                        public String getUserID() {
                            return userID;
                        }
                        public void setName(String name) {
                            this.name = name;
                        }
                        public void setPassword(String password) {
                            this.password = password;
                        }
                        public void setHashedPassword(String hashedPassword) {
                            this.hashedPassword = hashedPassword;
                        }
                        public void setTicketsBooked(List<Ticket> ticketsBooked) {
                            this.ticketsBooked = ticketsBooked;
                        }
                        public void setUserID(String userID) {
                            this.userID = userID;
                        }
                        public void printTicketsBooked(){
                            if (ticketsBooked == null || ticketsBooked.isEmpty()) {
                                System.out.println("No tickets booked.");
                            } else {
                                System.out.println("Tickets booked by " + name + ":");
                                for (int i = 0; i < ticketsBooked.size(); i++) {
                                    System.out.println(ticketsBooked.get(i).getTicketInfo());

                                }
                            }
                        }


}
