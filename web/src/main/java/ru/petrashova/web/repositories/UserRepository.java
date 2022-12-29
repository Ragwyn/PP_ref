package ru.petrashova.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.petrashova.web.models.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
//    @Query("select u from User u join fetch u.roles where l.login=:login")
    Optional<User> findByLogin(String login);


}

/*
Repository позволит оперировать объектом в БД вместо DAO
JpaRepository – это интерфейс фреймворка Spring Data предоставляющий набор стандартных методов JPA для работы с БД.
На основе этого интерфейса Spring Data предоставит реализации с методами, которые мы использовали в Entity Manager

Для создания Repository нужно придерживаться несколько правил:
1 – Имя репозитория должно начинаться с имени сущности NameReposytory (необязательно).
2 – Второй Generic должен быть оберточным типом того типа которым есть ID нашей сущности (обязательно).
3 – Первый Generic должен быть объектом нашей сущности для которой мы создали Repository, это указывает на то,
    что Spring Data должен предоставить реализацию методов для работы с этой сущностью (обязательно).
4 – Мы должны унаследовать свой интерфейс от JpaRepository, иначе Spring Data не предоставит реализацию для
    нашего репозитория (обязательно).

Но напрямую использовать Repositories для получение данных на Пользовательский Интерфейс не принято и считается
плохим тоном, для этого были придуманы Services
 */