package bot.repositories.db
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

@Document(collection = "AccountProducts")
data class AccountProducts(@Id val id: String? = null, var pingsSourceChannelId: String)

@Document(collection= "oProductsLinkNEW")
data class OProductsLinkNew(@Id val id: String? = null,
                            val timeWhenToSendMessage: Instant,
                            val commandName: String,
                            val args: List<String>)

interface IAccountProductsRepository : MongoRepository<AccountProducts, String>

interface IOProductsLinkNew : MongoRepository<OProductsLinkNew, String> {
    fun findByTimeWhenToSendMessageBefore(before: Instant): List<OProductsLinkNew>
}