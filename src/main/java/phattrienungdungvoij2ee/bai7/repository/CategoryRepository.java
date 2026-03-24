package phattrienungdungvoij2ee.bai7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoij2ee.bai7.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}