package org.example.repository;


import org.example.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByCategoryOrderByDisplayOrderAsc(String category);
    List<Skill> findAllByOrderByDisplayOrderAsc();
}
