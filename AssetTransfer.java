package TradeFinance;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;

@Contract(name = "TradeFinance", info = @Info(title = "TradeFinance contract", description = "Trade Finance chaincode", version = "0.0.1-SNAPSHOT"))

@Default
public final class AssetTransfer implements ContractInterface {

	private final Genson genson = new Genson();

	private enum TradeFinanceErrors {
		LC_NOT_FOUND, LC_ALREADY_EXISTS
	}

	/**
	 * Add some initial properties to the ledger
	 *
	 * @param ctx the transaction context Id Expirydate Buyer Bank Seller Amount
	 *            Status
	 */
	@Transaction()
	public void initLedger(final Context ctx) {

		ChaincodeStub stub = ctx.getStub();

		Asset asset = new Asset("1", "01-01-24", "Sachin", "SBI", "Jabbar", "100", "R");

		String assetState = genson.serialize(asset);

		stub.putStringState("1", assetState);
	}

	/**
	 * Add new Asset on the ledger.
	 *
	 *
	 * @param ctx        the transaction context
	 * @param id         is a Letter of credit ID
	 * @param Expirydate is the expiry date of the letter of credit
	 * @param Buyer      is the buyer of the goods
	 * @param Bank       is the one who does the finance
	 * @param Seller     is the one who who sells the goods
	 * @param Amount     to be paid by the buyer to seller
	 * @param Status     of Letter of Credit
	 * @return the created L/C
	 */

	@Transaction()
	public Asset requestLC(final Context ctx, final String id, final String expirydate, final String buyer,
			final String bank, final String seller, final String amount, final String status) {

		ChaincodeStub stub = ctx.getStub();

		String AssetState = stub.getStringState(id);

		if (!AssetState.isEmpty()) {
			String errorMessage = String.format("Asset %s already exists", id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LC_ALREADY_EXISTS.toString());
		}

		Asset asset = new Asset(id, expirydate, buyer, bank, seller, amount, status);

		AssetState = genson.serialize(asset);

		stub.putStringState(id, AssetState);

		return asset;
	}

	/**
	 * Issue letter of credit to a seller by the buyer's bank.
	 *
	 * @param ctx the transaction context
	 * @param id  the key
	 * @return the Letter Of Credit with updated status LC should be present in the
	 *         ledger.
	 */
	@Transaction()
	public Asset issueLc(final Context ctx, final String id) {
		ChaincodeStub stub = ctx.getStub();

		String AssetState = stub.getStringState(id);

		if (AssetState.isEmpty()) {
			String errorMessage = String.format("Asset %s does not exist", id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LC_NOT_FOUND.toString());
		}

		Asset asset = genson.deserialize(AssetState, Asset.class);

		Asset newAsset = new Asset(asset.getId(), asset.getExpiryDate(), asset.getBuyer(), asset.getBank(),
				asset.getSeller(), asset.getAmount(), "I");

		String newAssetState = genson.serialize(newAsset);

		stub.putStringState(id, newAssetState);

		return newAsset;
	}

	/**
	 * Accept the letter of credit This function helps to accept the letter of
	 * credit.
	 * 
	 * @param ctx the transaction context
	 * @param id  the key
	 * @return the Letter Of Credit with updated status LC should be present in the
	 *         ledger.
	 */
	@Transaction()
	public Asset acceptLc(final Context ctx, final String id) {
		ChaincodeStub stub = ctx.getStub();

		String AssetState = stub.getStringState(id);

		if (AssetState.isEmpty()) {
			String errorMessage = String.format("Asset %s does not exist", id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LC_NOT_FOUND.toString());
		}

		Asset asset = genson.deserialize(AssetState, Asset.class);

		Asset newAsset = new Asset(asset.getId(), asset.getExpiryDate(), asset.getBuyer(), asset.getBank(),
				asset.getSeller(), asset.getAmount(), "A");

		String newAssetState = genson.serialize(newAsset);

		stub.putStringState(id, newAssetState);

		return newAsset;
	}

	/**
	 * View the letter of credit This function helps to retrieve the Letter of
	 * Credit details from the ledger. Input Parameters
	 * 
	 * @param ctx the transaction context
	 * @param id  the key
	 * @return the Letter Of Credit details
	 */
	@Transaction()
	public Asset viewLcById(final Context ctx, final String id) {
		ChaincodeStub stub = ctx.getStub();
		String assetState = stub.getStringState(id);

		if (assetState.isEmpty()) {
			String errorMessage = String.format("Asset %s does not exist", id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LC_NOT_FOUND.toString());
		}

		Asset asset = genson.deserialize(assetState, Asset.class);
		return asset;
	}

}
