package demo;

public class VoterId {
	
	final String idCardNumber;

	public VoterId(String idCardNumber) {
		if (idCardNumber == null) {
			throw new NullPointerException("parameter idCardNumber is mandatory");
		}
		this.idCardNumber = idCardNumber;
	}

	@Override
	public int hashCode() {
		return idCardNumber.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VoterId other = (VoterId) obj;
		return idCardNumber.equals(other.idCardNumber);
	}

}
