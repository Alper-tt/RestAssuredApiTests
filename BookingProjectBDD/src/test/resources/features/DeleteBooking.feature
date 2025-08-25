Feature: Kullanici otel rezervasyonunu iptal etsin

  Scenario: Kullanici otel rezervasyonu olustursun ve rezervasyonu silsin
    Given Kullanici yeni bir rezervasyon olustursun
    And Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    And Kullanici rezervasyonu iptal etsin
    Then Rezervasyon basarili bir sekilde silinsin
