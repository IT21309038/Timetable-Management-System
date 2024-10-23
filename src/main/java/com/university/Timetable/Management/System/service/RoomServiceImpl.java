package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.RoomCollectionException;
import com.university.Timetable.Management.System.model.Room;
import com.university.Timetable.Management.System.repo.RoomRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepo roomRepo;

    @Override
    public void createRoom(Room room) throws ConstraintViolationException, RoomCollectionException {
        Optional<Room> roomOptional = roomRepo.findByRoomCode(room.getRoomCode());
        if(roomOptional.isPresent()){
            throw new RoomCollectionException(RoomCollectionException.AlreadyExists());
        }else {
            room.setCreatedAt(new Date(System.currentTimeMillis()));
            roomRepo.save(room);
        }
    }

    @Override
    public List<Room> getAllRoom() {
        List<Room> room = roomRepo.findAll();
        if (!room.isEmpty()){
            return room;
        }else {
            return new ArrayList<Room>();
        }
    }

    @Override
    public Room getSingleRoomByRoomCode(String roomCode) throws RoomCollectionException {
        Optional<Room> optRoom = roomRepo.findByRoomCode(roomCode);
        if(!optRoom.isPresent()){
            throw new RoomCollectionException(RoomCollectionException.NotFoundException(roomCode));
        }else {
            return optRoom.get();
        }
    }

    @Override
    public void updateRoom(String id, Room room) throws RoomCollectionException {
        Optional<Room> roomWithId = roomRepo.findById(id);

        Optional<Room> roomWithSameCode = roomRepo.findByRoomCode(room.getRoomCode());

        if(roomWithId.isPresent()){
            if(roomWithSameCode.isPresent() && !roomWithSameCode.get().getId().equals(id)){
                throw new RoomCollectionException(RoomCollectionException.AlreadyExists());
            }
            Room roomToUpdate = roomWithId.get();

            roomToUpdate.setRoomCode(room.getRoomCode());
            roomToUpdate.setRoomCapacity(room.getRoomCapacity());
            roomToUpdate.setFloor(room.getFloor());
            roomToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            roomRepo.save(roomToUpdate);
        }else {
            throw new RoomCollectionException(RoomCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteRoomById(String id) throws RoomCollectionException {
        Optional<Room> room = roomRepo.findById(id);
        if(!room.isPresent()){
            throw new RoomCollectionException(RoomCollectionException.NotFoundException(id));
        }else {
            roomRepo.deleteById(id);
        }
    }
}
