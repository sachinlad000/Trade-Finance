package TradeFinance;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import java.util.Objects;

@DataType()
public final class Asset {

	@Property()
	private final String id;

	@Property()
	private final String expirydate;

	@Property()
	private final String buyer;

	@Property()
	private final String bank;

	@Property()
	private final String seller;

	@Property()
	private final String amount;

	@Property()
	private final String status;

	public String getId() {
		return id;
	}

	public String getExpiryDate() {
		return expirydate;
	}

	public String getBuyer() {
		return buyer;
	}

	public String getBank() {
		return bank;
	}

	public String getSeller() {
		return seller;
	}

	public String getAmount() {
		return amount;
	}

	public String getStatus() {
		return status;
	}

	public Asset(@JsonProperty("id") final String id, @JsonProperty("expirydate") final String expirydate,
			@JsonProperty("buyer") final String buyer, @JsonProperty("bank") final String bank,
			@JsonProperty("seller") final String seller, @JsonProperty("amount") final String amount,
			@JsonProperty("status") final String status) {
		this.id = id;
		this.expirydate = expirydate;
		this.buyer = buyer;
		this.bank = bank;
		this.seller = seller;
		this.amount = amount;
		this.status = status;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}

		Asset other = (Asset) obj;

		return Objects.deepEquals(
				new String[] { getId(), getExpiryDate(), getBuyer(), getBank(), getSeller(), getAmount(), getStatus() },
				new String[] { other.getId(), other.getExpiryDate(), other.getBuyer(), other.getBank(),
						other.getSeller(), other.getAmount(), other.getStatus() });
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getExpiryDate(), getBuyer(), getBank(), getSeller(), getAmount(), getStatus());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [id=" + id + ", expDate="
				+ expirydate + ", buyer=" + buyer + ", bank=" + bank + ",seller=" + seller + ",amount=" + amount
				+ ",status=" + status + "]";
	}

}
