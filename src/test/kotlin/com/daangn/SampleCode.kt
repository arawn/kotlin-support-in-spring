package com.daangn

import java.util.Locale
import java.util.UUID

fun loadValue(): Any? = null

/* 데이터 보관을 목적으로 사용하는 클래스가 필요할 때는 class 앞에 data 를 붙여 정의한다
 * 프로퍼티에 대한 getter(), setter(), equals(), hashCode(), toString(), copy(), componentN()
 * 메소드를 컴파일 시점에 자동으로 생성한다.
 */
data class Person(
    val id: UUID,
    val firstname: String,
    val lastname: String,
    val address: Address
)

class Address(val city: String)

class PersonRepository {

    fun findByLastname(lastname: String) = listOf<Person>()
}

data class Traveler(
    val name: String,
    val city: String = "Seoul",
    val cash: Int = 0
) {
    fun moveTo(city: String) = copy(city = city)
    fun earn(cash: Int) = copy(cash = this.cash.plus(cash))
    fun pay(cash: Int) = copy(cash = this.cash.minus(cash))
}

class TravelerRepository {

    fun findByName(name: String): Traveler? = null
}

enum class CreditScore {
    BAD, FAIR, GOOD, EXCELLENT
}

// 단일표현 함수는 등호로 함수 정의와 바디를 구분하여 짧게 표현할 수 있다.
fun double(x: Int): Int = x * 2

fun main() {
    val repository = PersonRepository()
    val persons = repository.findByLastname("Matthews")

    val filteredPersons = persons.filter { it.address.city == "서울" }

    val number = 2021
    val beDoubled = double(number)

    val nullable: String? = null // 널이 될 수 있음
    val nonNullable: String = "" // 널이 될 수 없음

    val value = loadValue()
    if (value is String) {
        value.uppercase(Locale.getDefault()) // 문자열 타입이 제공하는 메서드를 사용 할 수 있음
    }

    val scoreRange = when (CreditScore.EXCELLENT) {
        CreditScore.BAD -> 300..629
        CreditScore.FAIR -> 630..689
        CreditScore.GOOD -> 690..719
        CreditScore.EXCELLENT -> 720..850
    }

    val travelerRepository = TravelerRepository()

    val arawn = Traveler("arawn", "Seoul", 1000)
    arawn.moveTo("New York")
    arawn.pay(10)

    val grizz = Traveler("Grizz", "Seoul", 1000).let {
        it.moveTo("London")
        it.pay(10)
    }

    val dan = Traveler("Dan").apply {
        moveTo("Vancouver")
        earn(50)
    }
    with(dan) {
        moveTo("Busan")
        moveTo("Seoul")
    }

    travelerRepository.findByName("Root")?.run {
        moveTo("Firenze")
    }

}
