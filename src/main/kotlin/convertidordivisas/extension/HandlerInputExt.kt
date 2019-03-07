package convertidordivisas.extension

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Request
import com.amazon.ask.model.Slot
import com.amazon.ask.request.Predicates
import java.util.function.Predicate

/**
 * Shortcuts. Why? Because of the law of demeter tell us:
 *          "You should only talk to friends"
 * So we say to our friend: "Hey give me that" instead of:
 *      "Gimme that thing to get that other thing"
 */
val HandlerInput.intentRequest: IntentRequest
    get() = requestEnvelope.request as IntentRequest

val HandlerInput.slotsMap: Map<String, Slot>
    get() = intentRequest.slotsMap

/**
 * Make things more readable with sentences
 */
inline fun <reified T : Request> HandlerInput.isRequestType(): Boolean =
        matches(Predicates.requestType(T::class.java))


/**
 * Extension function to create a readable way for matching a name in string format
 *
 * @param name a String representing an intent
 */
infix fun HandlerInput.matchesName(name: String): Boolean = matches(Predicates.intentName(name))

/**
 * Overriding the `get` operator allow us to write cool stuff between `[]`
 * like:
 *
 *          `input[Intents.Amazon.STOP orIntent Intents.Amazon.CANCEL]`
 *
 * in [convertidordivisas.handlers.CancelAndStopIntentHandler]
 * Unfortunately if we want to do similar stuff with other types we need to override the operator
 * once for every type parameter. We can do a generic implementation also.
 */
operator fun HandlerInput.get(predicate: Predicate<HandlerInput>): Boolean = matches(predicate)