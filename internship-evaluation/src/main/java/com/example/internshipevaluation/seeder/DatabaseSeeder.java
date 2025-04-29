package com.example.internshipevaluation.seeder;

import com.github.javafaker.Faker;
import com.example.internshipevaluation.entity.*;
import com.example.internshipevaluation.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class DatabaseSeeder {

    @Autowired
    private InternRepository internRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private CompetencyRepository competencyRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    // Lists of realistic data
    private final List<String> firstNames = Arrays.asList(
            "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth",
            "David", "Barbara", "Richard", "Susan", "Joseph", "Jessica", "Thomas", "Sarah", "Charles", "Karen"
    );
    private final List<String> lastNames = Arrays.asList(
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"
    );
    private final List<String> itCompanies = Arrays.asList(
            "Google", "Microsoft", "Amazon", "Apple", "Facebook", "IBM", "Oracle", "Intel", "Cisco", "Adobe",
            "Salesforce", "SAP", "NVIDIA", "Dell", "HP", "VMware", "Red Hat", "Atlassian", "ServiceNow", "Snowflake",
            "Palantir", "CrowdStrike", "Zscaler", "Okta", "Splunk", "GitLab", "GitHub", "MongoDB", "Datadog", "Twilio",
            "Slack", "Zoom", "Dropbox", "Box", "Asana", "Trello", "Figma", "Canva", "Notion", "Airtable",
            "Stripe", "PayPal", "Square", "Shopify", "Etsy", "Uber", "Lyft", "DoorDash", "Instacart", "Robinhood"
    );
    private final List<String> itDomains = Arrays.asList(
            "gmail.com", "outlook.com", "yahoo.com", "company.com", "techbit.com", "itpro.com", "coder.org", "devhub.com"
    );
    private final List<String> projectThemes = Arrays.asList(
            "Developing a REST API with Spring Boot", "Building a React.js Frontend for Task Management",
            "Designing a MySQL Database for E-commerce", "Implementing CI/CD with Jenkins and Docker",
            "Creating a Machine Learning Model with TensorFlow", "Developing a Mobile App with Flutter",
            "Optimizing Cloud Infrastructure with AWS", "Securing APIs with OAuth 2.0",
            "Building a Microservices Architecture with Kubernetes", "Automating Tests with Selenium",
            "Integrating Payment Gateways with Stripe", "Developing a Chat Application with WebSocket",
            "Creating a Data Pipeline with Apache Kafka", "Building a Dashboard with Angular and D3.js",
            "Implementing a Search Engine with Elasticsearch", "Developing a Blockchain Smart Contract",
            "Optimizing SQL Queries for Performance", "Building a Recommendation System with Python",
            "Creating a Scalable Backend with Node.js", "Designing a UI/UX Prototype with Figma"
    );
    private final List<String> objectives = Arrays.asList(
            "Learn to design and implement RESTful APIs using Spring Boot and best practices.",
            "Develop a responsive frontend application using React.js and integrate with backend APIs.",
            "Design and optimize a MySQL database schema for a real-world e-commerce application.",
            "Set up a CI/CD pipeline using Jenkins and Docker to automate deployment processes.",
            "Train a machine learning model using TensorFlow to predict user behavior.",
            "Build a cross-platform mobile app using Flutter and deploy it to app stores.",
            "Migrate an application to AWS and optimize for cost and performance.",
            "Implement OAuth 2.0 to secure API endpoints and manage user authentication.",
            "Deploy a microservices architecture using Kubernetes and monitor with Prometheus.",
            "Write automated tests using Selenium to ensure application reliability.",
            "Integrate Stripe for payment processing and handle transactions securely.",
            "Develop a real-time chat application using WebSocket and Node.js.",
            "Create a data pipeline with Apache Kafka to process streaming data.",
            "Build an interactive dashboard with Angular and D3.js for data visualization.",
            "Implement a full-text search engine using Elasticsearch for a web application.",
            "Develop and deploy a smart contract on Ethereum using Solidity.",
            "Analyze and optimize SQL queries to improve database performance.",
            "Build a recommendation system using Python and collaborative filtering.",
            "Create a scalable backend with Node.js and MongoDB for a web application.",
            "Design a user-friendly UI/UX prototype using Figma for a mobile app."
    );
    private final List<String> competencyNames = Arrays.asList(
            "Java Programming", "Spring Boot Development", "React.js Frontend Development", "MySQL Database Design",
            "Docker Containerization", "Kubernetes Orchestration", "AWS Cloud Management", "TensorFlow Machine Learning",
            "Flutter Mobile Development", "Selenium Test Automation", "OAuth 2.0 Security", "Node.js Backend Development",
            "Apache Kafka Data Streaming", "Angular Framework", "D3.js Data Visualization", "Elasticsearch Search Implementation",
            "Blockchain Development", "SQL Query Optimization", "Python Data Science", "UI/UX Design with Figma"
    );
    private final List<String> observationTemplates = Arrays.asList(
            "The intern showed great initiative in learning %s and successfully applied it to the project.",
            "Needs improvement in %s, but demonstrated a strong willingness to learn.",
            "Excellent work on %s, completed tasks ahead of schedule with high quality.",
            "Struggled with %s initially, but made significant progress by the end of the internship.",
            "Consistently exceeded expectations in %s, contributing valuable insights to the team.",
            "Demonstrated strong teamwork while working on %s, collaborating effectively with peers.",
            "Could improve time management skills when working on %s, but overall performance was satisfactory.",
            "Showed exceptional creativity in %s, leading to innovative solutions for the project.",
            "Performed well in %s, but needs to focus on improving communication with the team.",
            "Mastered %s quickly and became a key contributor to the project’s success."
    );

    @PostConstruct
    public void seedDatabase() {
        // Clear existing data
        competencyRepository.deleteAll();
        evaluationRepository.deleteAll();
        internshipRepository.deleteAll();
        tutorRepository.deleteAll();
        companyRepository.deleteAll();
        internRepository.deleteAll();

        // Seed Interns (500 interns)
        List<Intern> interns = new ArrayList<>();
        Set<String> usedEmails = new HashSet<>();
        int internCounter = 1;
        for (int i = 0; i < 500; i++) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            String baseEmail = firstName.toLowerCase() + "." + lastName.toLowerCase();
            String emailDomain = itDomains.get(random.nextInt(itDomains.size()));
            String email = baseEmail + "@" + emailDomain;

            // Ensure email uniqueness
            while (usedEmails.contains(email)) {
                email = baseEmail + internCounter + "@" + emailDomain;
                internCounter++;
            }
            usedEmails.add(email);

            Intern intern = Intern.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .build();
            try {
                interns.add(internRepository.save(intern));
            } catch (DataIntegrityViolationException e) {
                System.out.println("Skipping duplicate intern email: " + email);
                continue;
            }
        }

        // Seed Companies (100 companies)
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String companyName = i < itCompanies.size() ? itCompanies.get(i) : faker.company().name() + " Tech";
            Company company = Company.builder()
                    .name(companyName)
                    .address(faker.address().streetAddress() + ", " + faker.address().city() + ", " + faker.address().country())
                    .build();
            companies.add(companyRepository.save(company));
        }

        // Seed Tutors (300 tutors)
        List<Tutor> tutors = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            Tutor tutor = Tutor.builder()
                    .firstName(firstNames.get(random.nextInt(firstNames.size())))
                    .lastName(lastNames.get(random.nextInt(lastNames.size())))
                    .company(companies.get(random.nextInt(companies.size())))
                    .build();
            tutors.add(tutorRepository.save(tutor));
        }

        // Seed Internships (1000 internships)
        List<Internship> internships = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            LocalDate startDate = faker.date().past(730, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = startDate.plusDays(random.nextInt(90) + 30); // Internship duration between 30-120 days
            Internship internship = Internship.builder()
                    .intern(interns.get(random.nextInt(interns.size())))
                    .company(companies.get(random.nextInt(companies.size())))
                    .tutor(tutors.get(random.nextInt(tutors.size())))
                    .startDate(startDate)
                    .endDate(endDate)
                    .projectTheme(projectThemes.get(random.nextInt(projectThemes.size())))
                    .objectives(objectives.get(random.nextInt(objectives.size())))
                    .build();
            internships.add(internshipRepository.save(internship));
        }

        // Seed Evaluations (1000 evaluations, one per internship)
        List<Evaluation> evaluations = new ArrayList<>();
        for (Internship internship : internships) {
            String skill = competencyNames.get(random.nextInt(competencyNames.size()));
            String observation = String.format(
                    observationTemplates.get(random.nextInt(observationTemplates.size())),
                    skill
            );
            Evaluation evaluation = Evaluation.builder()
                    .internship(internship)
                    .implication(getRandomImplicationLevel())
                    .openness(getRandomOpennessLevel())
                    .quality(getRandomQualityLevel())
                    .observations(observation)
                    .build();
            evaluations.add(evaluationRepository.save(evaluation));
        }

        // Seed Competencies (5000 competencies, 4-6 per evaluation)
        for (Evaluation evaluation : evaluations) {
            int numCompetencies = random.nextInt(3) + 4; // 4-6 competencies per evaluation
            for (int i = 0; i < numCompetencies; i++) {
                Competency.CompetencyType type = getRandomCompetencyType();
                Competency competency = Competency.builder()
                        .evaluation(evaluation)
                        .name(competencyNames.get(random.nextInt(competencyNames.size())))
                        .level(getRandomCompetencyLevel())
                        .type(type)
                        .score(type == Competency.CompetencyType.COMPANY || type == Competency.CompetencyType.SCIENTIFIC_TECHNICAL ? random.nextInt(21) : null)
                        .build();
                competencyRepository.save(competency);
            }
        }

        System.out.println("Database seeding completed!");
        System.out.println("Interns: " + interns.size());
        System.out.println("Companies: " + companies.size());
        System.out.println("Tutors: " + tutors.size());
        System.out.println("Internships: " + internships.size());
        System.out.println("Evaluations: " + evaluations.size());
        System.out.println("Competencies: " + competencyRepository.count());
    }

    private Evaluation.ImplicationLevel getRandomImplicationLevel() {
        Evaluation.ImplicationLevel[] levels = Evaluation.ImplicationLevel.values();
        return levels[random.nextInt(levels.length)];
    }

    private Evaluation.OpennessLevel getRandomOpennessLevel() {
        Evaluation.OpennessLevel[] levels = Evaluation.OpennessLevel.values();
        return levels[random.nextInt(levels.length)];
    }

    private Evaluation.QualityLevel getRandomQualityLevel() {
        Evaluation.QualityLevel[] levels = Evaluation.QualityLevel.values();
        return levels[random.nextInt(levels.length)];
    }

    private Competency.CompetencyLevel getRandomCompetencyLevel() {
        Competency.CompetencyLevel[] levels = Competency.CompetencyLevel.values();
        return levels[random.nextInt(levels.length)];
    }

    private Competency.CompetencyType getRandomCompetencyType() {
        Competency.CompetencyType[] types = Competency.CompetencyType.values();
        return types[random.nextInt(types.length)];
    }
}