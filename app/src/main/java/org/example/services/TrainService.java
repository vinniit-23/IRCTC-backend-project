package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrainService
{
    private Train train;
    private List<Train> trainList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String Train_path = "app/src/main/java/org/example/localDb/train.json";
    private static final String User_path = "app/src/main/java/org/example/localDb/users.json";

    public List<Train> loadTrains() throws Exception{
        File trainFile = new File(Train_path);
        return objectMapper.readValue(trainFile, new TypeReference<List<Train>>() {
        });
    }

    public TrainService()throws Exception{
        loadTrains();
    }
    public TrainService (Train train)throws Exception{
        this.train=train;
        loadTrains();
    }

                  public List<Train> searchTrains(String source,String destination){
                      return trainList.stream().filter(train1 ->
                              validTrain(train,source,destination)).collect(Collectors.toList());
                  }

                  public Boolean validTrain(Train train,String source,String destination) {
                      List<String> stationOrder = train.getStations();

                      int sourceIndex = stationOrder.indexOf(source.toLowerCase());
                      int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

                      return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;

                  }
                  public void bookSeat(int row, int col, Train train, Ticket ticket,
                                       List<Train> trainList, User user){
                        if (train.getSeats().get(row).get(col) == 0) {
                            train.getSeats().get(row).set(col, 1);
                            System.out.println("Seat booked successfully.");
                            // adding tkt to the user database
                            ticket.setTicketId(UUID.randomUUID().toString());
                            ticket.setUserId(user.getUserID());
                            ticket.setSource(train.getStations().get(0));
                            ticket.setDestination(train.getStations().get(train.getStations().size()-1));
                            //newTkt.setDateOfTravel() already done
                            ticket.setTrain(train);

                            addTicket(ticket);

                        } else {
                            System.out.println("Seat is already booked.");
                        }
                        
                  }
                    public void addTicket(Ticket ticket) {
                        try {
                            List<User> users = objectMapper.readValue(new File(User_path),
                                    new TypeReference<List<User>>() {});
                            for (User user : users) {
                                if (user.getUserID().equals(ticket.getUserId())) {
                                    user.getTicketsBooked().add(ticket);
                                    objectMapper.writeValue(new File(User_path), users);
                                    System.out.println("Ticket added to user bookings.");
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error adding ticket: " + e.getMessage());
                        }
                    }
}
