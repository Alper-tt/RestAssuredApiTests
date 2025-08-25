Feature: Kullanici otel rezervasyonu oluştursun
  Scenario: Kullanici otel rezervasyonu oluştursun
    Given Kullanici yeni bir rezervasyon olustursun
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun