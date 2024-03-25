# Trade-Finance
Hyperledger, Java
This code provides a basic implementation of a trade finance smart contract using Hyperledger Fabric. It defines a data structure (Asset) representing an asset, and a smart contract (AssetTransfer) that initializes the ledger, handles L/C requests, issues and accepts L/Cs, and allows viewing L/C details. The code follows best practices such as proper encapsulation, error handling, and usage of annotations for Hyperledger Fabric integration.
The code consists of two parts. The first part is the definition of a data class named Asset, and the second part is a smart contract named AssetTransfer written using Hyperledger Fabric's chaincode in Java. Let's break down and discuss each part separately:

1. Asset Class:
This class represents a data structure for an asset,for a Letter of Credit (L/C) in a trade finance scenario. Here are the key points about the Asset class:
Properties:
•	id: A unique identifier for the asset.
•	expirydate: Expiry date of the asset.
•	buyer: Buyer of the asset.
•	bank: The financial institution involved.
•	seller: Seller of the asset.
•	amount: The amount associated with the asset.
•	status: Status of the asset, indicating the current state of a Letter of Credit. R=Requested,I=Issued,A=Accepted

Getters: There are getters for each property to retrieve their values.
Constructor: A parameterized constructor is defined using @JsonProperty annotations to map JSON properties to object properties.
Equals and HashCode: Overrides equals and hashCode methods to compare objects based on their properties.
ToString: Overrides toString method to provide a readable string representation of the object.


2. AssetTransfer Smart Contract:
This class is the Hyperledger Fabric smart contract handling trade finance transactions. Key functionalities include initializing the ledger, requesting a Letter of Credit (L/C), issuing an L/C, accepting an L/C, and viewing L/C details. Here are the key points:
Initialization: The initLedger method initializes the ledger with an sample Asset instance.
requestLC Method:
•	Accepts parameters for creating a new Asset (L/C).
•	Checks if an asset with the given ID already exists.
•	Serializes the Asset object and stores it in the ledger.
issueLc and acceptLc Methods:
•	Handle issuing and accepting an L/C, respectively.
•	Retrieve the existing L/C from the ledger.
•	Update the L/C status to "I" (issued) or "A" (accepted).
•	Serialize and store the updated L/C in the ledger.
viewLcById Method:
•	Retrieves an L/C from the ledger based on its ID.

Enum TradeFinanceErrors: Defines error types for the smart contract.
Genson Object: Utilizes the Genson library for JSON serialization and deserialization.
