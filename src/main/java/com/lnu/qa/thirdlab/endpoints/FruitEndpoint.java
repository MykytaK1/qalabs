package com.lnu.qa.thirdlab.endpoints;

import com.lnu.qa.thirdlab.models.gen.*;
import com.lnu.qa.thirdlab.repository.FruitRepository;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@AllArgsConstructor
public class FruitEndpoint {
    private static final String NAMESPACE_URL = "http://www.qa.lnu.com/thirdlab/models/gen";

    private FruitRepository fruitRepository;

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "removeAllFruitsRequest")
    @ResponsePayload
    public RemoveAllFruitsResponse removeAllFruits() {
        fruitRepository.removeAllFruits();
        return new RemoveAllFruitsResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getFruitsRequest")
    @ResponsePayload
    public GetFruitsResponse getFruits() {
        GetFruitsResponse response = new GetFruitsResponse();
        response.getFruits().addAll(fruitRepository.retrieveAllFruits());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getFruitRequest")
    @ResponsePayload
    public GetFruitResponse getFruit(@RequestPayload GetFruitRequest request) {
        GetFruitResponse response = new GetFruitResponse();
        response.setFruit(fruitRepository.getFruitById(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "createFruitRequest")
    @ResponsePayload
    public CreateFruitResponse createFruit(@RequestPayload CreateFruitRequest request) {
        CreateFruitResponse response = new CreateFruitResponse();
        response.setFruit(fruitRepository.saveFruit(request.getFruit()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "updateFruitRequest")
    @ResponsePayload
    public UpdateFruitResponse updateFruit(@RequestPayload UpdateFruitRequest request) {
        UpdateFruitResponse response = new UpdateFruitResponse();
        response.setFruit(fruitRepository.updateFruit(request.getFruit()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "removeFruitRequest")
    @ResponsePayload
    public RemoveFruitResponse removeFruit(@RequestPayload RemoveFruitRequest request) {
        RemoveFruitResponse response = new RemoveFruitResponse();
        response.setFruit(fruitRepository.removeFruitById(request.getId()));
        return response;
    }

}
