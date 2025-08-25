Feature: Kullanici otel rezervasyonu oluştursun
  Scenario: Kullanici otel rezervasyonu oluştursun
    Given Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun