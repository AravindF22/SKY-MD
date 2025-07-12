package utils;

import com.github.javafaker.Faker;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestData {
    //Faker is initialized only once, improving performance
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
    private final int allergyReactionOneIndex;
    private final int allergyReactionTwoIndex;
    private final int allergyReactionThreeIndex;
    private final int allergyCategoryOneIndex;
    private final int allergyCategoryTwoIndex;
    private final int allergyCategoryThreeIndex;
    private final int medicationOneIndex;
    private final int medicationTwoIndex;
    private final int dosageOneValue;
    private final int dosageTwoValue;
    private final int medicationFormOneIndex;
    private final int medicationFormTwoIndex;
    private final int medicationFrequencyOneIndex;
    private final int medicationFrequencyTwoIndex;
    private final int medicationPerOneIndex;
    private final int medicationPerTwoIndex;
    private final int concernIndex;
    private final int symptomsOneIndex;
    private final int sufferingConditionDays;
    private final int statusIndex;
    private final int selectDaysIndex;
    private final int severityIndex;
    private final int bodyPartsIndex;
    private final int lifeStyleItemsIndex;
    private final String optionalFieldText;
    //BH
    private final int therapyReasonsIndex;
    private final int smokingItemsIndex;
    private final int symptomsListIndex;
    private final int medicalConditionIndex;
    private final int exerciseStatusIndex;
    private final int alcoholStatusIndex;
    private final int therapistHistoryIndex;
    private final String reasonForPastTherapistVisit;
    private final int hospitalizedHistoryIndex;
    private final int suicidalThoughtIndex;
    private final int hospitalizedReasonIndex;
    private final String additionalFieldText;
    private final int questionnaireOneIndex;
    private final int questionnaireTwoIndex;
    private final int questionnaireThreeIndex;
    private final int questionnaireFourIndex;
    private final int questionnaireFiveIndex;
    private final int questionnaireSixIndex;
    private final int questionnaireSevenIndex;
    private final int questionnaireEightIndex;
    private final int questionnaireNineIndex;
    private final int questionnaireTenIndex;
    //Primary care
    private final int conditionsIndex;
    private final int generalSymptomsIndex;
    private final int skinSymptomsIndex;
    private final int headSymptomsIndex;
    private final int earSymptomsIndex;
    private final int eyeSymptomsIndex;
    private final int visionSymptomsIndex;
    private final int noseSymptomsIndex;
    private final int mouthThroatSymptomsIndex;
    private final int neckSymptomsIndex;
    private final int breastSymptomsIndex;
    private final int respiratorySymptomsIndex;
    private final int cardiovascularSymptomsIndex;
    private final int gastrointestinalSymptomsIndex;
    private final int urinarySymptomsIndex;
    private final int genitalSymptomsIndex;
    private final int femaleSymptomsIndex;
    private final int vascularSymptomsIndex;
    private final int musculoskeletalSymptomsIndex;
    private final int neurologicSymptomsIndex;
    private final int hematologicSymptomsIndex;
    private final int endocrineSymptomsIndex;
    private final int psychiatricSymptomsIndex;

    private Random random;
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
        this.allergyReactionOneIndex = faker.number().numberBetween(0,alleryReaction.length);
        this.allergyReactionTwoIndex = faker.number().numberBetween(0,alleryReaction.length);
        this.allergyReactionThreeIndex = faker.number().numberBetween(0,alleryReaction.length);
        this.allergyCategoryOneIndex = faker.number().numberBetween(0,allergyCategory.length);
        this.allergyCategoryTwoIndex = faker.number().numberBetween(0,allergyCategory.length);
        this.allergyCategoryThreeIndex = faker.number().numberBetween(0,allergyCategory.length);
        this.medicationOneIndex = faker.number().numberBetween(0, medications.length);
        this.medicationTwoIndex = faker.number().numberBetween(0, medications.length);
        this.sufferingConditionDays = faker.number().numberBetween(1,100);
        this.concernIndex = faker.number().numberBetween(0, concerns.length);
        this.symptomsOneIndex = faker.number().numberBetween(0, symptoms.length);
        this.statusIndex = faker.number().numberBetween(0, status.length);
        this.selectDaysIndex = faker.number().numberBetween(0,selectDays.length);
        this.severityIndex = faker.number().numberBetween(0, severity.length);
        this.bodyPartsIndex = faker.number().numberBetween(0, bodyParts.length);
        this.lifeStyleItemsIndex = faker.number().numberBetween(0,lifeStyleItems.length);
        this.optionalFieldText = faker.lorem().word();
        this.therapyReasonsIndex = faker.number().numberBetween(0,therapyReasons.length);
        this.smokingItemsIndex = faker.number().numberBetween(0, SmokingItems.length);
        this.symptomsListIndex = faker.number().numberBetween(0, symptomsList.length);
        this.medicalConditionIndex = faker.number().numberBetween(0, MedicalConditions.length);
        this.exerciseStatusIndex = faker.number().numberBetween(0, exerciseStatuses.length);
        this.alcoholStatusIndex = faker.number().numberBetween(0,alcoholStatuses.length);
        this.therapistHistoryIndex = faker.number().numberBetween(0, therapistHistorySts.length);
        this.reasonForPastTherapistVisit = faker.lorem().word();
        this.hospitalizedHistoryIndex = faker.number().numberBetween(0, hospitalizedHistorySts.length);
        this.suicidalThoughtIndex = faker.number().numberBetween(0,suicidalThoughts.length);
        this.hospitalizedReasonIndex = faker.number().numberBetween(0, hospitalizedReason.length);
        this.additionalFieldText = faker.lorem().word();
        this.questionnaireOneIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireTwoIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireThreeIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireFourIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireFiveIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireSixIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireSevenIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireEightIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireNineIndex = faker.number().numberBetween(0, questionnaireOptions.length);
        this.questionnaireTenIndex = faker.number().numberBetween(0, questionnaireTenOptions.length);
        this.medicationFormOneIndex = faker.number().numberBetween(0, medicationForms.length);
        this.medicationFormTwoIndex = faker.number().numberBetween(0, medicationForms.length);
        this.medicationFrequencyOneIndex  = faker.number().numberBetween(0, medicationFrequencies.length);
        this.medicationFrequencyTwoIndex  = faker.number().numberBetween(0, medicationFrequencies.length);
        this.medicationPerOneIndex  = faker.number().numberBetween(0, medicationPerOptions.length);
        this.medicationPerTwoIndex  = faker.number().numberBetween(0, medicationPerOptions.length);
        this.dosageOneValue = faker.number().numberBetween(1, 501);
        this.dosageTwoValue = faker.number().numberBetween(1, 501);
        this.conditionsIndex = faker.number().numberBetween(0, conditions.length);
        this.generalSymptomsIndex = faker.number().numberBetween(0, generalSymptoms.length);
        this.skinSymptomsIndex = faker.number().numberBetween(0, skinSymptoms.length);
        this.headSymptomsIndex = faker.number().numberBetween(0, headSymptoms.length);
        this.earSymptomsIndex = faker.number().numberBetween(0, earSymptoms.length);
        this.eyeSymptomsIndex = faker.number().numberBetween(0, eyeSymptoms.length);
        this.visionSymptomsIndex = faker.number().numberBetween(0, visionSymptoms.length);
        this.noseSymptomsIndex = faker.number().numberBetween(0, noseSymptoms.length);
        this.mouthThroatSymptomsIndex = faker.number().numberBetween(0, mouthThroatSymptoms.length);
        this.neckSymptomsIndex = faker.number().numberBetween(0, neckSymptoms.length);
        this.breastSymptomsIndex = faker.number().numberBetween(0, breastSymptoms.length);
        this.respiratorySymptomsIndex = faker.number().numberBetween(0, respiratorySymptoms.length);
        this.cardiovascularSymptomsIndex = faker.number().numberBetween(0, cardiovascularSymptoms.length);
        this.gastrointestinalSymptomsIndex = faker.number().numberBetween(0, gastrointestinalSymptoms.length);
        this.urinarySymptomsIndex = faker.number().numberBetween(0, urinarySymptoms.length);
        this.genitalSymptomsIndex = faker.number().numberBetween(0, genitalSymptoms.length);
        this.femaleSymptomsIndex = faker.number().numberBetween(0, femaleSymptoms.length);
        this.vascularSymptomsIndex = faker.number().numberBetween(0, vascularSymptoms.length);
        this.musculoskeletalSymptomsIndex = faker.number().numberBetween(0, musculoskeletalSymptoms.length);
        this.neurologicSymptomsIndex = faker.number().numberBetween(0, neurologicSymptoms.length);
        this.hematologicSymptomsIndex = faker.number().numberBetween(0, hematologicSymptoms.length);
        this.endocrineSymptomsIndex = faker.number().numberBetween(0, endocrineSymptoms.length);
        this.psychiatricSymptomsIndex = faker.number().numberBetween(0, psychiatricSymptoms.length);
    }
    String [] insurances = {"AETNA", "Blue Cross / Blue Shield of Texas", "MEDICARE","ANTHEM BLUE CROSS","Medical Mutual of Ohio (Zelis)"};
    String [] relationship = {"Self","Spouse","Parent","Other"};
    String[] symptoms = {
            "Arthritis", "Bleeding", "Blistering", "Bruising", "Burning", "Changing Color",
            "Changing Shape", "Comes and Goes", "Crusted", "Dark Skin Spots", "Dry Skin", "Elevated", "Enlarging",
            "Fever", "Flaring", "Flushing", "Inflamed", "Irritated", "Itching", "Loss of Skin Pigment", "Painful",
            "Redness", "Rough", "Scarring", "Sores in Mouth", "Spreading", "Stable", "Swelling", "Tender",};
    String[] concerns = {
            "Acne", "Cold Sores", "Cosmetic Procedure Follow Up",
            "Dandruff", "Excessive Sweating", "Eyelash Thinning", "Hair Loss",
            "Nail Issue", "Rash", "Rosacea", "Skin Aging", "Skin Discoloration",
            "Skin Lesion", "Referred by RA Fisher", "Referred by WartPEEL"};
    String[] bodyParts = {
            "Abdomen", "Ankle - Left",
            "Ankle - Right", "Anus",
            "Arm - Lower Left",
            "Arm - Lower Right",
            "Arm - Upper Left"};
    String[] status = {"Improving", "Worsening", "Unchanged"};
    String[] selectDays = {"Day(s)", "Month(s)", "Year(s)"};
    String[] severity = {"Mild", "Moderate", "Severe"};
    String[] lifeStyleItems = {
            "Excessive Sun Exposure",
            "High Carb Diet", "Indoor Tanning",
            "Smoker", "Stress", "Travel Abroad", "None"};

    String[] medications = {"Anu-Med", "Beta Med", "Perio Med", "Medi-Sleep", "Medi-Mucil", "Mediwash", "Medi-Tabs","Medi Pads", "Medi-Patch",
            "Medi-Phedryl", "Medi-Bismuth", "Solu-Medrol", "Solu-Medrol (PF)", "Mediproxen", "Medi-Seltzer", "Psoriasis Medicated", "Allergy Medication",
            "Allergy Medicine","Mediwash Eye Irrigant", "medroxyprogesterone", "Testim", "Testopel", "Testred", "First-Testosterone", "FIRST-Testosterone MC",
            "testosterone", "Depo-Testosterone", "spherule-derv Cocci skin test", "testosterone cypionate", "testosterone enanthate", "testosterone propionate",
            "candida albicans skin test", "testosterone undecanoate"};
    String[] units = {"mg", "ml", "g", "units", "drops", "puffs"};

    String[] medicationForms = {
            "Capsule", "Cream", "Foam", "Gel", "Implant", "Injection", "Liquid",
            "Lotion", "Oil", "Ointment", "Patch", "Ring", "Shampoo", "Tablet", "Other"
    };
    String[] medicationFrequencies = {
            "Once", "Twice","Three", "Four", "Five", "Six", "PRN", "Other"
    };
    String[] medicationPerOptions = {
            "Hour", "Day", "Week", "Month", "Year", "PRN", "Other"
    };
    String[] allergies = {
            "Cats", "Ca Phosphate/Cats Claw", "Uncaria Tomentosa (Cats Claw)", "Testim", "Testoderm",
            "Testolactone", "Testopel Implant Pellet", "Testred Oral Capsule", "Testosterone","Testosterone (Eqv-Testim)",
            "Testopel Pellets", "Testosterone Cypionate", "Testoderm(obsolete)", "Testolactone(obsolete)",
            "Testolin(obsolete)", "Testomar(obsolete)", "Testred(obsolete)", "Testro(obsolete)",
            "Depo-Testadiol", "Bar-Test", "Depo-Testosterone", "Active Test", "Advocate Test", "Dogs"};

    String[] alleryReaction = {
            "Anaphylaxis", "Brachycardia", "Chest Pain", "Conjunctivitis",
            "Cough", "Diarrhea", "Difficulty speaking or swallowing", "Dizziness/Lightheadedness", "Facial swelling", "Hives",
            "Irregular Heartbeat", "Itchiness", "Loss of consciousness", "Nausea", "Rash", "Respiratory Distress",
            "Runny nose", "Shortness of breath", "Tachycardia", "Tongue swelling", "Vomiting Wheezing"};

    String [] allergyCategory = {"Drug or Medication Allergy", "Environment or Food Allergy"};

    String[] SmokingItems = {
            "Cigarettes", "Vape",
            "Cannabis", "Chew",
            "Cigar", "Pipe"};
    String[] therapyReasons = {
            "Feeling depressed", "Experiencing anxiety",
            "Mood is impacting areas of my life", "Relationship conflicts",
            "Feelings of purposelessness/ lack of meaning",
            "A recent loss - grief", "Recent or past trauma",
            "Working through a challenge", "Building self-worth, " +
            "self confidence", "Personal improvement", "Parenting",
            "LGBTQ+ matters", "Religion", "Social anxiety", "Alcohol abuse"};
    String[] symptomsList = {
            "Decreased appetite", "Increased appetite",
            "Trouble concentrating", "Difficulty sleeping", "Excessive sleep", "Low motivation",
            "Isolation from others", "Fatigue/Low energy", "Excessive energy", "Low Self-esteem",
            "Depressed mood", "Tearful or crying spells", "Anxiety", "Regretful", "Fear",
            "Compulsive", "Procrastination", "Decreased libido", "Increased libido"
    };
    String[] MedicalConditions = {
            "Asthma",
            "Arthritis", "Cancer", "Skin Cancer",
            "Headaches / Migraines", "Hepatitis",
            "Anemia", "High Blood Pressure (HTN)",
            "Abnormal Cholesterol", "Diabetes"
    };
    String [] therapistHistorySts = {"Yes", "No"};
    String [] hospitalizedHistorySts = {"Yes", "No"};

    String [] exerciseStatuses ={"Yes", "No"};
    String [] alcoholStatuses = {"Never", "Occasionally", "Daily", "Previously/Quit"};

    String[] suicidalThoughts = {
            "Never", "Over a year ago",
            "Over the last several months",
            "This week", "Currently"
    };
    String[]  hospitalizedReason = {
            "Recent or past hospitalizations for psychopathy, hallucinations, delusions, substance abuse",
            "Recent inpatient treatment for active suicidality, self harm, or assault committed to others",
            "Recent inpatient treatment for severe substance abuse of any kind - especially opioid, fentanyl, alcohol",
            "Recent arrest or hospitalization for mental health concern"
    };
    String[] questionnaireOptions = {
            "Not at all", "Several days", "More than half the days","Nearly every day"
    };
    String[] questionnaireTenOptions = {
            "Not difficult at all","Somewhat difficult", "Very difficult", "Extremely difficult"
    };
    String[] conditions = {
            "Cold & flu symptoms", "Allergies", "Covid-19 symptoms", "Sinus-related symptoms",
            "Cough, breathing or asthma symptoms", "Sore throat / Infections", "Diabetes-related symptoms",
            "Painful joints", "Headache, migraine, ear ache", "Back problems",
            "Depression, fatigue, low energy, anxiety", "Stomach bug, abdominal pain, cramps",
            "Sprains, strains, bruises and wounds", "Urinary / bladder symptoms", "I just don't feel good overall."
    };
    String[] generalSymptoms = {"Weight loss", "Fatigue", "Weakness", "Trouble sleeping"};

    String[] skinSymptoms = {"Rash", "Itching", "Dryness", "Hair or Nail changes", "Color changes"};

    String[] headSymptoms = {"Headache", "Head injury"};

    String[] earSymptoms = {"Changes in hearing", "Ringing in ears (tinnitus)", "Earache", "Drainage"};

    String[] eyeSymptoms = {"Vision problems", "Pain", "Redness", "Blurry or double"};

    String[] visionSymptoms = {"Flashing lights", "Specks / floaters"};

    String[] noseSymptoms = {"Stuffiness", "Discharge", "Itching", "Nose bleeds", "Sinus pain"};


    String[] mouthThroatSymptoms = {"Sore throat", "Dry mouth", "Hoarseness", "Non-healing sores"};

    String[] neckSymptoms = {"Lumps", "Swollen glands", "Pain", "Stiffness"};

    String[] breastSymptoms = {"Lumps", "Pain", "Discharge"};

    String[] respiratorySymptoms = {
            "Cough", "Sputum", "Coughing up blood", "Shortness of breath", "Wheezing",
            "Painful breathing", "Difficulty breathing lying down", "Snoring"
    };

    String[] cardiovascularSymptoms = {
            "Chest pain or discomfort", "Chest tightness", "Palpitations", "Swelling (edema)",
            "Sudden awakening from sleep with shortness of breath"
    };

    String[] gastrointestinalSymptoms = {
            "Swallowing difficulties", "Heartburn", "Change in appetite", "Nausea",
            "Change in bowel habits", "Rectal bleeding", "Constipation", "Diarrhea"
    };

    String[] urinarySymptoms = {
            "Frequency", "Urgency", "Burning or pain with urination", "Blood in urine",
            "Incontinence", "Change in urinary strength"
    };

    String[] genitalSymptoms = {
            "Pain with sex", "STD's", "Hernia", "Mass / pain / sores", "Itch / rash"
    };

    String[] femaleSymptoms = {"Vaginal dryness", "Hot flashes", "Vaginal discharge"};

    String[] vascularSymptoms = {"Calf pain with walking", "Leg cramps"};

    String[] musculoskeletalSymptoms = {
            "Muscle or joint pain", "Joint stiffness", "Back pain", "Redness of joints",
            "Swelling of joints", "Trauma"
    };

    String[] neurologicSymptoms = {
            "Dizziness", "Fainting", "Seizures", "Weakness", "Numbness", "Tingling", "Tremor"
    };

    String[] hematologicSymptoms = {"Ease of bruising", "Ease of bleeding"};

    String[] endocrineSymptoms = {"Heat or cold intolerance", "Sweating", "Increased thirst"};

    String[] psychiatricSymptoms = {
            "Nervousness", "Depression", "Anxiety", "Memory loss / concerns", "Stress"
    };

    // mandatory details
    public String getFullName(){return fname+" "+lname;}
    public String getFname() {
        return fname;
    }
    public String getLname() {return lname;}
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
    public String getDobForMajor() {
        String format = Boolean.parseBoolean(ConfigReader.getProperty("gitHubActions")) ? "MM/dd/yyyy" : "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dobForMajor);
    }
    public String getDobForMajorInMMDD() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dobForMajor);
    }
    public String getDobForMinor(){
        String format = Boolean.parseBoolean(ConfigReader.getProperty("gitHubActions")) ? "MM/dd/yyyy" : "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dobForMinor); // e.g., "03/15/1990"
    }
    public String getDobForMinorInMMDD(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dobForMinor);
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
    public String getWholeHeight(){return feet+"."+inch;}
    public String getWeight(){return weight;}
    //primary insurance
    public String getMemberNameForPrimaryInsurance(){return fname+" "+lname;}
    public String getMemberIdForPrimaryInsurance(){ return memberIdForPrimaryInsurance;}
    public String getMemberDobForPrimaryInsurance() {
        String format = Boolean.parseBoolean(ConfigReader.getProperty("gitHubActions")) ? "MM/dd/yyyy" : "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dobForMajor);
    }
    public String getPrimaryInsurance(){
        return insurances[primaryInsuranceIndex];}
    //secondary insurance
    public String getSecondaryInsurance(){
        return insurances[secondaryInsuranceIndex];}
    public String getMemberNameForSecondaryInsurance(){
        return memberNameForSecondaryInsurance;}
    public String getMemberIdForSecondaryInsurance(){
        return memberIdForSecondaryInsurance;}
    public String getMemberDobForSecondaryInsurance() {
        String format = Boolean.parseBoolean(ConfigReader.getProperty("gitHubActions")) ? "MM/dd/yyyy" : "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(memberDobForSecondaryInsurance);
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
    public String getMedicationTwo(){
        return medications[medicationTwoIndex];
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
        return alleryReaction[allergyReactionOneIndex];
    }
    public String getAllergyReactionTwo(){
        return alleryReaction[allergyReactionTwoIndex];
    }
    public String getAllergyReactionThree(){
        return alleryReaction[allergyReactionThreeIndex];
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
    public String getConcern(){
        return concerns[concernIndex];}
    public String getSymptomOne(){
        return symptoms[symptomsOneIndex];}
    public String getStatus(){
        return status[statusIndex];}
    public String getSeverity(){
        return severity[severityIndex];}
    public String getSelectDays(){
        return selectDays[selectDaysIndex];}
    public String getSufferingConditionDays(){
        return String.valueOf(sufferingConditionDays);}
    public String getBodyParts(){
        return bodyParts[bodyPartsIndex];}
    public String getLifeStyleItem(){
        return lifeStyleItems[lifeStyleItemsIndex];}
    public String getWhatMakesWorse(){
        random = new Random();
        int count = 5 + random.nextInt(6); // Random number between 5 and 10
        String worse = IntStream.range(0, count)
                .mapToObj(i -> faker.lorem().word())
                .collect(Collectors.joining(", "));
        return worse;
    }
    public String getWhatMakesBetter(){
        random = new Random();
        int count = 5 + random.nextInt(6); // Random number between 5 and 10
        String better = IntStream.range(0, count)
                .mapToObj(i -> faker.lorem().word())
                .collect(Collectors.joining(", "));
        return better;
    }
    public String getOptionalFieldText(){
        return optionalFieldText;
    }
    public String getTherapyReasons(){
        return therapyReasons[therapyReasonsIndex];
    }
    public String getSmokingItem(){
        return SmokingItems[smokingItemsIndex];
    }
    public String getSymptoms(){
        return symptomsList[symptomsListIndex];
    }
    public String getMedicalCondition(){
        return MedicalConditions[medicalConditionIndex];
    }
    public String getExerciseStatus(){
        return exerciseStatuses[exerciseStatusIndex];
    }
    public String getAlcoholStatus(){
        return alcoholStatuses[alcoholStatusIndex];
    }
    public String getTherapistHistoryStatus(){
        return therapistHistorySts[therapistHistoryIndex];
    }
    public String getHospitalizedHistoryStatus(){
        return hospitalizedHistorySts[hospitalizedHistoryIndex];
    }
    public String getSuicidalThoughtStatus(){
        return suicidalThoughts[suicidalThoughtIndex];
    }
    public String getReasonForPastTherapistVisit(){
        return reasonForPastTherapistVisit;
    }
    public String getHospitalizedReason(){
        return hospitalizedReason[hospitalizedReasonIndex];
    }
    public String getAdditionalText(){
        return additionalFieldText;
    }
    public String getQuestionnaireOne(){
        return questionnaireOptions[questionnaireOneIndex];
    }
    public String getQuestionnaireTwo() {
        return questionnaireOptions[questionnaireTwoIndex];
    }

    public String getQuestionnaireThree() {
        return questionnaireOptions[questionnaireThreeIndex];
    }

    public String getQuestionnaireFour() {
        return questionnaireOptions[questionnaireFourIndex];
    }

    public String getQuestionnaireFive() {
        return questionnaireOptions[questionnaireFiveIndex];
    }

    public String getQuestionnaireSix() {
        return questionnaireOptions[questionnaireSixIndex];
    }

    public String getQuestionnaireSeven() {
        return questionnaireOptions[questionnaireSevenIndex];
    }

    public String getQuestionnaireEight() {
        return questionnaireOptions[questionnaireEightIndex];
    }

    public String getQuestionnaireNine() {
        return questionnaireOptions[questionnaireNineIndex];
    }

    public String getQuestionnaireTen() {
        return questionnaireTenOptions[questionnaireTenIndex];
    }
    public String getMedicationFormOne(){
        return medicationForms[medicationFormOneIndex];
    }
    public String getMedicationFormTwo(){
        return medicationForms[medicationFormTwoIndex];
    }
    public String getMedicationFrequencyOne(){
        return medicationFrequencies[medicationFrequencyOneIndex];
    }
    public String getMedicationFrequencyTwo(){
        return medicationFrequencies[medicationFrequencyTwoIndex];
    }
    public String getMedicationPerOne(){
        return medicationPerOptions[medicationPerOneIndex];
    }
    public String getMedicationPerTwo(){
        return medicationPerOptions[medicationPerTwoIndex];
    }
    public String getDosageOne() {
        String unit = units[faker.random().nextInt(units.length)];
        return dosageOneValue + " " + unit;
    }
    public String getDosageTwo(){
        String unit = units[faker.random().nextInt(units.length)];
        return dosageTwoValue + " " + unit;
    }
    public String getCondition(){
        return conditions[conditionsIndex];
    }
    public String getGeneralSymptom() { return generalSymptoms[generalSymptomsIndex]; }
    public String getSkinSymptom() { return skinSymptoms[skinSymptomsIndex]; }
    public String getHeadSymptom() { return headSymptoms[headSymptomsIndex]; }
    public String getEarSymptom() { return earSymptoms[earSymptomsIndex]; }
    public String getEyeSymptom() { return eyeSymptoms[eyeSymptomsIndex]; }
    public String getVisionSymptom() { return visionSymptoms[visionSymptomsIndex]; }
    public String getNoseSymptom() { return noseSymptoms[noseSymptomsIndex]; }
    public String getMouthThroatSymptom() { return mouthThroatSymptoms[mouthThroatSymptomsIndex]; }
    public String getNeckSymptom() { return neckSymptoms[neckSymptomsIndex]; }
    public String getBreastSymptom() { return breastSymptoms[breastSymptomsIndex]; }
    public String getRespiratorySymptom() { return respiratorySymptoms[respiratorySymptomsIndex]; }
    public String getCardiovascularSymptom() { return cardiovascularSymptoms[cardiovascularSymptomsIndex]; }
    public String getGastrointestinalSymptom() { return gastrointestinalSymptoms[gastrointestinalSymptomsIndex]; }
    public String getUrinarySymptom() { return urinarySymptoms[urinarySymptomsIndex]; }
    public String getGenitalSymptom() { return genitalSymptoms[genitalSymptomsIndex]; }
    public String getFemaleSymptom() { return femaleSymptoms[femaleSymptomsIndex]; }
    public String getVascularSymptom() { return vascularSymptoms[vascularSymptomsIndex]; }
    public String getMusculoskeletalSymptom() { return musculoskeletalSymptoms[musculoskeletalSymptomsIndex]; }
    public String getNeurologicSymptom() { return neurologicSymptoms[neurologicSymptomsIndex]; }
    public String getHematologicSymptom() { return hematologicSymptoms[hematologicSymptomsIndex]; }
    public String getEndocrineSymptom() { return endocrineSymptoms[endocrineSymptomsIndex]; }
    public String getPsychiatricSymptom() { return psychiatricSymptoms[psychiatricSymptomsIndex]; }
}

