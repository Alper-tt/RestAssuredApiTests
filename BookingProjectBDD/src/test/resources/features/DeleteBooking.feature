Feature: Kullanici otel rezervasyonunu iptal etsin
  Scenario: Kullanici otel rezervasyonu olustursun ve rezervasyonu silsin
    Given Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    When Kullanici rezervasyonu iptal etsin
    Then Rezervasyon basarili bir sekilde silinsin
