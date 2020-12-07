package de.seriousdonkey.pocketbeans.app

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.time.DateTime
import java.lang.reflect.Type

class ApiDateTimeDeserializer : JsonDeserializer<DateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DateTime {
        return DateTime.parse(json!!.asString)
    }
}