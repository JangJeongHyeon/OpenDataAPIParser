package dalab.main;

import dalab.provider.MedicineAPI;
import dalab.provider.PopulationAPI;
import dalab.service.medicine.ATC4Code;
import dalab.service.medicine.AddressCode;
import dalab.service.population.SearchPopulation;

import java.util.Scanner;

/**
 * Created by Jang Jeong Hyeon on 2017-08-07.
 */
public class Main {

    public static void main(String args[]){
//        PopulationAPI populationAPI = new PopulationAPI();
//        populationAPI.registrationService("totalService",new SearchPopulation());
//        populationAPI.executeService("totalService");
        MedicineAPI medicineAPI = new MedicineAPI();
        medicineAPI.registrationService("ATCStep4CodeService", new ATC4Code());
//        medicineAPI.registrationService("AddressCodeService",new AddressCode());
        medicineAPI.executeService("ATCStep4CodeService");
//        medicineAPI.executeService("AddressCodeService");
    }
}
