package edu.umass.cs.sase.stream;

public class ActivityEvent implements Event {

	/**
	 * Event id
	 */
	int id;

	/**
	 * Event timestamp
	 */
	int timestamp;
	/**
	 * Event type
	 */
	String eventType;
	int activityId;
	int rte;

	public ActivityEvent(int id, int timestamp, int activityId, int heartRate) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.activityId = activityId;
		this.rte = heartRate;
	}

	public ActivityEvent(int id, int timestamp, String eventType, int activityId, int heartRate) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.eventType = eventType;
		this.activityId = activityId;
		this.rte = heartRate;
	}

	@Override
	public int getAttributeByName(String attributeName) {
		if (attributeName.equalsIgnoreCase("activityId"))
			return this.activityId;
		if (attributeName.equalsIgnoreCase("rte"))
			return rte;

		if (attributeName.equalsIgnoreCase("id"))
			return this.id;
		if (attributeName.equalsIgnoreCase("timestamp"))
			return this.timestamp;

		return 0;
	}

	@Override
	public double getAttributeByNameDouble(String attributeName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getAttributeByNameString(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAttributeValueType(String attributeName) {
		// TODO Auto-generated method stub
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
	public Object clone(){
		ActivityEvent o = null;
		try {
			o = (ActivityEvent)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		return "ActivityEvent [id=" + id + ", timestamp=" + timestamp + ", eventType=" + eventType + ", activityId="
				+ activityId + ", heartRate=" + rte + "]";
	}
	
}
