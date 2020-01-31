package higherkindess.compendiumtest

import higherkindness.compendiumtest.{Product, Supplier}
import kantan.csv.RowDecoder

object Decoder {

  implicit val productDecoder: RowDecoder[Product] = RowDecoder.ordered {
    (id: String, desc: String, color: String, size: String) =>
      Product(id, desc, color, size)
  }

  implicit val supplierDecoder: RowDecoder[Supplier] = RowDecoder.ordered {
    (id: String, name: String, email: String, phone: String) =>
      Supplier(id, name, email, phone)
  }
}
