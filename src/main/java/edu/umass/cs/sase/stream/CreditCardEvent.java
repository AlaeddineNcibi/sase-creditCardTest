package edu.umass.cs.sase.stream;

public class CreditCardEvent implements Event {

	int id; // Event id
	int timestamp;
	String eventType;
	String crdId;
	int ltion;
	double mount;

	public CreditCardEvent(int id, int timestamp, String cardId, double amount, int location) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.crdId = cardId;
		this.mount = amount;
		this.ltion = location;
	}

	public CreditCardEvent(int id, int timestamp, String eventType, String cardId, double amount, int location) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.crdId = cardId;
		this.mount = amount;
		this.ltion = location;
		this.eventType = eventType;
	}

	@Override
	public int getAttributeByName(String attributeName) {

		if (attributeName.equalsIgnoreCase("id"))
			return this.id;
		if (attributeName.equalsIgnoreCase("timestamp"))
			return this.timestamp;
		if (attributeName.equalsIgnoreCase("ltion"))
			return this.ltion;

		return 0;
	}

	@Override
	public double getAttributeByNameDouble(String attributeName) {
		if (attributeName.equalsIgnoreCase("mount"))
			return this.mount;
		return 0;
	}

	@Override
	public String getAttributeByNameString(String attributeName) {

		if (attributeName.equalsIgnoreCase("crdId"))
			return this.crdId;

		if (attributeName.equalsIgnoreCase("eventType"))
			return this.eventType;
		return null;
	}

	@Override
	public int getAttributeValueType(String attributeName) {
		if (attributeName.equalsIgnoreCase("id"))
			return 0;
		if (attributeName.equalsIgnoreCase("timestamp"))
			return 0;
		if (attributeName.equalsIgnoreCase("ltion"))
			return 0;
		if (attributeName.equalsIgnoreCase("mount"))
			return 1;
		if (attributeName.equalsIgnoreCase("crdId"))
			return 2;
		if (attributeName.equalsIgnoreCase("eventType"))
			return 2;
		return 0;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(int Id) {
		// TODO Auto-generated method stub
		this.id = Id;
	}

	@Override
	public int getTimestamp() {
		// TODO Auto-generated method stub
		return this.timestamp;
	}

	@Override
	public String getEventType() {
		// TODO Auto-generated method stub
		return this.eventType;
	}

	/**
	 * Clones the event
	 */
	public Object clone() {
		CreditCardEvent o = null;
		try {
			o = (CreditCardEvent) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		return "CreditCardEvent [id=" + id + ", timestamp=" + timestamp + ", eventType=" + eventType + ", cardId="
				+ crdId + ", amount=" + mount + ", location=" + ltion + "]";
	}

}