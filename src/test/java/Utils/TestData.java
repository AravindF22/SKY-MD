package Utils;

import com.github.javafaker.Faker;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TestData {
    private final Faker faker = new Faker();
    private final String fname;
    private final String lname;
    private final String email;
    private final String mobileNumber;
    private final String zipCode;
    private final String providerName ;
    private final String referralClinic;
    private final String referralClinicState;
    private final String streetAddressOne ;
    private final String streetAddressTwo;
    private final String gender;
    private final Date dobForMajor;
    private final Date dobForMinor;
    private final String feet;
    private final String feetForMinor;
    private final String inch;
    private final String weight;
    private final String memberIdForPrimaryInsurance;
    private final int relationShipForPrimaryInsuranceIndex;
    private final int primaryInsuranceIndex;
    private final int secondaryInsuranceIndex;
    private final String memberNameForSecondaryInsurance;
    private final String memberIdForSecondaryInsurance;
    private final Date memberDobForSecondaryInsurance;
    private final int relationshipForSecondaryInsuranceIndex;
    private final int allergyOneIndex ;
    private final int allergyTwoIndex ;
    private final int allergyThreeIndex;
    private final int alleryReactionOneIndex ;
    private final int alleryReactionTwoIndex ;
    private final int alleryReactionThreeIndex;
    private final int allergyCategoryOneIndex;
    private final int allergyCategoryTwoIndex;
    private final int allergyCategoryThreeIndex;
    private final int medicationOneIndex;
    private final int medicationTwoIndex;

    public TestData() {
        this.fname = faker.name().firstName();
        this.lname = faker.name().lastName().replaceAll("[^a-zA-Z0-9]", "");
        this.email = (fname + lname + "01@yopmail.com").toLowerCase();
        this.mobileNumber = "715" + faker.phoneNumber().subscriberNumber(7);
        this.zipCode = "92121";
        this.providerName = "Dr. Janice Fahey";
        this.referralClinicState ="CA";
        this.referralClinic = "seattle";
        this.streetAddressOne = faker.address().streetName();
        this.streetAddressTwo = faker.address().streetAddress();
        this.gender = faker.demographic().sex();
        this.dobForMajor = faker.date().birthday(19, 70);
        this.dobForMinor = faker.date().birthday(1,18);
        this.feet = String.valueOf(faker.number().numberBetween(4, 10));
        this.feetForMinor = String.valueOf(faker.number().numberBetween(2, 10));
        this.inch = String.valueOf(faker.number().numberBetween(0, 11));
        this.weight = String.valueOf(faker.number().numberBetween(20, 50));
        this.memberIdForPrimaryInsurance = faker.regexify("[A-Z0-9]{10}");
        this.relationShipForPrimaryInsuranceIndex = faker.number().numberBetween(0,relationship.length);
        this.primaryInsuranceIndex = faker.number().numberBetween(0, insurances.length);
        this.secondaryInsuranceIndex = faker.number().numberBetween(0, insurances.length);
        this.memberNameForSecondaryInsurance = faker.name().fullName();
        this.memberIdForSecondaryInsurance = faker.regexify("[A-Z0-9]{10}");
        this.memberDobForSecondaryInsurance = faker.date().birthday(19,70);
        this.relationshipForSecondaryInsuranceIndex = faker.number().numberBetween(0,relationship.length);
        this.allergyOneIndex =faker.number().numberBetween(0, allergies.length);
        this.allergyTwoIndex = faker.number().numberBetween(0, allergies.length);
        this.allergyThreeIndex = faker.number().numberBetween(0, allergies.length);
        this.alleryReactionOneIndex = faker.number().numberBetween(0,alleryReaction.length);
        this.alleryReactionTwoIndex = faker.number().numberBetween(0,alleryReaction.length);
        this.alleryReactionThreeIndex = faker.number().numberBetween(0,alleryReaction.length);
        this.allergyCategoryOneIndex = faker.number().numberBetween(0,allergyCategory.length);
        this.allergyCategoryTwoIndex = faker.number().numberBetween(0,allergyCategory.length);
        this.allergyCategoryThreeIndex = faker.number().numberBetween(0,allergyCategory.length);
        this.medicationOneIndex = faker.number().numberBetween(0, medications.length);
        this.medicationTwoIndex = faker.number().numberBetween(0, medications.length);
    }
    String [] insurances = {"AETNA", "Blue Cross / Blue Shield of Texas", "MEDICARE","ANTHEM BLUE CROSS","Medical Mutual of Ohio (Zelis)"};
    String [] relationship = {"Self","Spouse","Parent","Other"};

    String[] medications = {"Anu-Med", "Beta Med", "Perio Med", "Medi-Sleep", "Medi-Mucil", "Mediwash", "Medi-Tabs","Medi Pads", "Medi-Patch",
            "Medi-Phedryl", "Medi-Bismuth", "Solu-Medrol", "Solu-Medrol (PF)", "Mediproxen", "Medi-Seltzer", "Psoriasis Medicated", "Allergy Medication",
            "Allergy Medicine","Mediwash Eye Irrigant", "medroxyprogesterone", "Testim", "Testopel", "Testred", "First-Testosterone", "FIRST-Testosterone MC",
            "testosterone", "Depo-Testosterone", "spherule-derv Cocci skin test", "testosterone cypionate", "testosterone enanthate", "testosterone propionate",
            "candida albicans skin test", "testosterone undecanoate"};

    String[] allergies = {
            "Cats", "Ca Phosphate/Cats Claw", "Uncaria Tomentosa (Cats Claw)",
            "Testim", "Testoderm", "testolactone",
            "Testopel", "Testred", "testosterone","Testosterone (Eqv-Testim)", "Testopel Pellets",
            "Testosterone Cypionate", "Testoderm(obsolete)", "Testolactone(obsolete)", "Testolin(obsolete)",
            "Testomar(obsolete)", "Testred(obsolete)", "Testro(obsolete)",
            "Depo-Testadiol", "Bar-Test", "Depo-Testosterone", "Active Test", "Advocate Test", "dogs"};

    String[] alleryReaction = {
            "Anaphylaxis", "Brachycardia", "Chest Pain", "Conjunctivitis",
            "Cough", "Diarrhea", "Difficulty speaking or swallowing", "Dizziness/Lightheadedness", "Facial swelling", "Hives",
            "Irregular Heartbeat", "Itchiness", "Loss of consciousness", "Nausea", "Rash", "Respiratory Distress",
            "Runny nose", "Shortness of breath", "Tachycardia", "Tongue swelling", "Vomiting Wheezing"};

    String [] allergyCategory = {"Drug or Medication Allergy", "Environment or Food Allergy"};
    // mandatory details
    public String getFullName(){
        return fname+" "+lname;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getEmail() {
        return email;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public String getZipCode(){ return zipCode;}
    public String getProviderName(){ return providerName;}

    //referral details
    public  String getReferralClinic(){
        return referralClinic;
    }
    public  String getReferralClinicState(){
        return referralClinicState;
    }

    //additional information
    public String getDobForMajor(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dobForMajor); // e.g., "03/15/1990"
    }
    public String getDobForMinor(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dobForMinor); // e.g., "03/15/1990"
    }
    public String getStreetAddressOne(){
        return streetAddressOne;
    }
    public String getStreetAddressTwo(){
        return streetAddressTwo;
    }
    public String getWholeAddress(){
        return streetAddressOne+" "+streetAddressTwo;
    }
    public String getGender(){return gender;}
    public String getFeet(){return feet;}
    public String getInch(){
        return inch;
    }
    public String getFeetForMinor(){
        return feetForMinor;
    }
    public String getWholeHeight(){
        return feet+"."+inch;
    }
    public String getWeight(){
        return weight;
    }
    //primary insurance
    public String getMemberNameForPrimaryInsurance(){
        return fname+" "+lname;
    }
    public String getMemberIdForPrimaryInsurance(){ return memberIdForPrimaryInsurance;}
    public String getMemberDobForPrimaryInsurance(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dobForMajor);

    }
    public String getPrimaryInsurance(){
        return insurances[primaryInsuranceIndex];
    }
    //secondary insurance
    public String getSecondaryInsurance(){
        return insurances[secondaryInsuranceIndex];
    }
    public String getMemberNameForSecondaryInsurance(){
        return memberNameForSecondaryInsurance;
    }
    public String getMemberIdForSecondaryInsurance(){
        return memberIdForSecondaryInsurance;
    }
    public String getMemberDobForSecondaryInsurance(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(memberDobForSecondaryInsurance); // e.g., "03/15/1990"
    }
    public String getRelationshipForPrimaryInsurance(){
        return relationship[relationShipForPrimaryInsuranceIndex];
    }
    public String getRelationshipForSecondaryInsurance(){
        return relationship[relationshipForSecondaryInsuranceIndex];
    }
    public String getMedicationOne(){
        return medications[medicationOneIndex];
    }
    public String getAllergyOne(){return allergies[allergyOneIndex];
    }
    public String getAllergyTwo(){
        return allergies[allergyTwoIndex];
    }
    public String getAllergyThree(){
        return allergies[allergyThreeIndex];
    }
    public String getAllergyReactionOne(){
        return alleryReaction[alleryReactionOneIndex];
    }
    public String getAllergyReactionTwo(){
        return alleryReaction[alleryReactionTwoIndex];
    }
    public String getAllergyReactionThree(){
        return alleryReaction[alleryReactionThreeIndex];
    }
    public String getAllergyCategoryOne(){
        return allergyCategory[allergyCategoryOneIndex];
    }
    public String getAllergyCategoryTwo(){
        return allergyCategory[allergyCategoryTwoIndex];
    }
    public String getAllergyCategoryThree(){
        return allergyCategory[allergyCategoryThreeIndex];
    }
    public String getDrugAllergyCategory(){
        return allergyCategory[0];
    }
    public String getEnvironmentAllergyCategory(){
        return allergyCategory[1];
    }
}

