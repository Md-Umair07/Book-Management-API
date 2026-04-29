package com.POC.Book.Management.API.Repository;

import com.POC.Book.Management.API.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}

