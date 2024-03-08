import ru.naumen.core.server.script.spi.*
import static ru.kazantsev.nsd.sdk.global_variables.ApiPlaceholder.*

Root_SDO root = utils.get('root', [:])
return root.UUID
