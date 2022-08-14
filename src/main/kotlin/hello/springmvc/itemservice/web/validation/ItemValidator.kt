package hello.springmvc.itemservice.web.validation

import hello.springmvc.itemservice.domain.item.Item
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class ItemValidator: Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return clazz.isAssignableFrom(Item::class.java)
    }

    override fun validate(target: Any, errors: Errors) {
        val item = target as Item

        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
            "itemName",
            "required"
        )

        if (item.price == null || item.price < 1000 || item.price > 1000000) {
            errors.rejectValue("price", "range", arrayOf(1000, 1000000), null)
        }

        if (item.quantity == null || item.quantity > 10000) {
            errors.rejectValue("quantity", "max", arrayOf(9999), null)
        }

        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price * item.quantity
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", arrayOf(10000, resultPrice), null)
            }
        }
    }
}