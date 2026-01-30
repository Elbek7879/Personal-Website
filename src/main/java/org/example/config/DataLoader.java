package org.example.config;


import lombok.RequiredArgsConstructor;
import org.example.model.Experience;
import org.example.model.Project;
import org.example.model.Skill;
import org.example.repository.ExperienceRepository;
import org.example.repository.ProjectRepository;
import org.example.repository.SkillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;

    @Override
    public void run(String... args) throws Exception {
        if (projectRepository.count() == 0) {
            loadProjects();
        }

        if (skillRepository.count() == 0) {
            loadSkills();
        }

        if (experienceRepository.count() == 0) {
            loadExperiences();
        }

        System.out.println("✅ Test data loaded successfully!");
    }

    private void loadProjects() {
        Project project1 = new Project();
        project1.setTitle("E-Commerce Backend API");
        project1.setDescription("Full-featured e-commerce REST API with Spring Boot, JWT authentication, payment integration (Stripe), and admin panel.");
        project1.setTechnologies("Java, Spring Boot, PostgreSQL, JWT, Docker");
        project1.setGithubUrl("https://github.com/yourusername/ecommerce-api");
        project1.setDemoUrl("https://ecommerce-demo.com");
        project1.setImageUrl("https://images.unsplash.com/photo-1557821552-17105176677c?w=600&h=400&fit=crop");
        project1.setFeatured(true);
        project1.setCreatedAt(LocalDateTime.now().minusMonths(2));
        projectRepository.save(project1);

        Project project2 = new Project();
        project2.setTitle("Task Management System");
        project2.setDescription("Real-time collaboration tool with WebSocket, file sharing, and project tracking features.");
        project2.setTechnologies("Spring Boot, WebSocket, MySQL, Redis");
        project2.setGithubUrl("https://github.com/yourusername/task-manager");
        project2.setImageUrl("https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=600&h=400&fit=crop");
        project2.setFeatured(true);
        project2.setCreatedAt(LocalDateTime.now().minusMonths(1));
        projectRepository.save(project2);

        Project project3 = new Project();
        project3.setTitle("Blog Platform API");
        project3.setDescription("RESTful blog platform with categories, tags, comments, and user authentication.");
        project3.setTechnologies("Java, Spring Boot, MongoDB, Spring Security");
        project3.setGithubUrl("https://github.com/yourusername/blog-api");
        project3.setImageUrl("https://images.unsplash.com/photo-1499750310107-5fef28a66643?w=600&h=400&fit=crop");
        project3.setFeatured(true);
        project3.setCreatedAt(LocalDateTime.now().minusWeeks(2));
        projectRepository.save(project3);

        System.out.println("✅ Projects loaded!");
    }

    private void loadSkills() {
        skillRepository.save(new Skill(null, "Java", 90, "Backend", "fab fa-java", 1));
        skillRepository.save(new Skill(null, "Spring Boot", 85, "Backend", "fas fa-leaf", 2));
        skillRepository.save(new Skill(null, "Spring Security", 80, "Backend", "fas fa-shield-alt", 3));
        skillRepository.save(new Skill(null, "REST API", 88, "Backend", "fas fa-server", 4));
        skillRepository.save(new Skill(null, "PostgreSQL", 85, "Database", "fas fa-database", 5));
        skillRepository.save(new Skill(null, "MySQL", 80, "Database", "fas fa-database", 6));
        skillRepository.save(new Skill(null, "MongoDB", 75, "Database", "fas fa-database", 7));
        skillRepository.save(new Skill(null, "Git", 82, "Tools", "fab fa-git-alt", 8));
        skillRepository.save(new Skill(null, "Docker", 70, "Tools", "fab fa-docker", 9));
        skillRepository.save(new Skill(null, "Microservices", 78, "Backend", "fas fa-cubes", 10));

        System.out.println("✅ Skills loaded!");
    }

    private void loadExperiences() {
        Experience exp1 = new Experience();
        exp1.setCompany("Tech Solutions LLC");
        exp1.setPosition("Senior Backend Developer");
        exp1.setDescription("Leading backend development team, designing microservices architecture, and optimizing API performance.");
        exp1.setStartDate(LocalDate.of(2023, 1, 1));
        exp1.setCurrent(true);
        exp1.setLocation("Tashkent, Uzbekistan");
        experienceRepository.save(exp1);

        Experience exp2 = new Experience();
        exp2.setCompany("Digital Agency");
        exp2.setPosition("Backend Developer");
        exp2.setDescription("Developed RESTful APIs using Spring Boot, integrated payment systems, and implemented JWT authentication.");
        exp2.setStartDate(LocalDate.of(2022, 1, 1));
        exp2.setEndDate(LocalDate.of(2022, 12, 31));
        exp2.setCurrent(false);
        exp2.setLocation("Tashkent, Uzbekistan");
        experienceRepository.save(exp2);

        Experience exp3 = new Experience();
        exp3.setCompany("Startup Company");
        exp3.setPosition("Junior Java Developer");
        exp3.setDescription("Built backend services with Spring Framework, participated in code reviews, and learned clean code principles.");
        exp3.setStartDate(LocalDate.of(2021, 6, 1));
        exp3.setEndDate(LocalDate.of(2021, 12, 31));
        exp3.setCurrent(false);
        exp3.setLocation("Tashkent, Uzbekistan");
        experienceRepository.save(exp3);

        System.out.println("✅ Experiences loaded!");
    }
}
