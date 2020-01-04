package com.mycompany.myapp.entitiesResources;

import com.mycompany.myapp.domain.Expiration;

import java.time.LocalDate;
import java.util.List;

public class ExpirationResource
{    

    public List<Expiration> expirationList;

    public void CheckDatesLogic()
    {        
        for (Expiration expiration : expirationList) {

            LocalDate actualEndDate = expiration.getEndDate();
            LocalDate actualWarningDate = actualEndDate.minusDays(30);

            if (actualEndDate.isBefore(LocalDate.now()))
            {
                expiration.setStatus("Vencido");
            }
            else if (actualWarningDate.isBefore(LocalDate.now()))
            {
                expiration.setStatus("A Vencer");
            }
        }
    }
}