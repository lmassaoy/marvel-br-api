package com.lyamada;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class MarvelCharacterResourceTest {

    @Test
    public void testGetById() {
        given()
                .when().get("/character/1011128")
                .then()
                .statusCode(200)
                .body("marvelId", is(1011128),
                        "descricaoEN", is("While Eddie Brock’s academic career seemed to take off, his personal life was in shambles due to his quick temper and inability to connect with women on any level, and Eddie became isolated, but when his old friend Peter Parker contacted him, he felt a rush of enthusiasm: here, at last, was someone with whom he could truly bond."),
                        "descricaoPT", is("Enquanto a carreira acadêmica de Eddie Brock parecia decolar, sua vida pessoal estava em ruínas devido ao seu temperamento rápido e incapacidade de se conectar com mulheres em qualquer nível, e Eddie ficou isolado, mas quando seu velho amigo Peter Parker entrou em contato com ele, sentiu uma pressa de entusiasmo: aqui, finalmente, havia alguém com quem ele poderia realmente se relacionar."),
                        "foto", is("http://i.annihil.us/u/prod/marvel/i/mg/e/10/531773a976816.jpg"),
                        "nome", is("Venom (Ultimate)"),
                        "wiki", is("http://marvel.com/universe/Venom_(Ultimate)?utm_campaign=apiRef&utm_source=9bd883247a88be2a1d1c4a015612c187"));
    }

    @Test
    public void testGetByName() {
        given()
                .when().get("/character?name=Venom (Ultimate)")
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "[0].marvelId", is(1011128),
                        "[0].descricaoEN", is("While Eddie Brock’s academic career seemed to take off, his personal life was in shambles due to his quick temper and inability to connect with women on any level, and Eddie became isolated, but when his old friend Peter Parker contacted him, he felt a rush of enthusiasm: here, at last, was someone with whom he could truly bond."),
                        "[0].descricaoPT", is("Enquanto a carreira acadêmica de Eddie Brock parecia decolar, sua vida pessoal estava em ruínas devido ao seu temperamento rápido e incapacidade de se conectar com mulheres em qualquer nível, e Eddie ficou isolado, mas quando seu velho amigo Peter Parker entrou em contato com ele, sentiu uma pressa de entusiasmo: aqui, finalmente, havia alguém com quem ele poderia realmente se relacionar."),
                        "[0].foto", is("http://i.annihil.us/u/prod/marvel/i/mg/e/10/531773a976816.jpg"),
                        "[0].nome", is("Venom (Ultimate)"),
                        "[0].wiki", is("http://marvel.com/universe/Venom_(Ultimate)?utm_campaign=apiRef&utm_source=9bd883247a88be2a1d1c4a015612c187"));
    }

}
