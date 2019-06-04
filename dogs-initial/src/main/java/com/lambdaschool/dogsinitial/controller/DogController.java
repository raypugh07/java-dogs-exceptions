package com.lambdaschool.dogsinitial.controller;

import com.lambdaschool.dogsinitial.Model.Dog;
import com.lambdaschool.dogsinitial.DogsinitialApplication;
import com.lambdaschool.dogsinitial.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    // localhost:8080/dogs/dogs
    @GetMapping(value = "/dogs")
    public ResponseEntity<?> getAllDogs()
    {
        return new ResponseEntity<>(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK);
    }

    // localhost:8080/dogs/{id}
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDogDetail(@PathVariable long id)
    {
        Dog rtnDog;
        if (DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id))== null)
        {
            throw new ResourceNotFoundException("Dog with id " + id + " not found");
        } else {
             rtnDog = DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id));
        }
            return new ResponseEntity<>(rtnDog, HttpStatus.OK);

    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}")
    public ResponseEntity<?> getDogBreeds (@PathVariable String breed)
    {
        ArrayList<Dog> rtnDogs = DogsinitialApplication.ourDogList.
                findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));
        return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
    }

    //localhost:2019/dogs/dogTable
    @GetMapping(value="/dogTable")
    public ModelAndView displayDogTable(){

        ModelAndView mav=new ModelAndView();
        mav.setViewName("dogs");
        mav.addObject("dogList",DogsinitialApplication.ourDogList.dogList);

        return mav;
    }
}
