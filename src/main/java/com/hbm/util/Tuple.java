package com.hbm.util;

import com.hbm.interfaces.Spaghetti;

@Spaghetti("alreay??") //i made this like a week ago and it's already eye-bleeding, what the fuck happened?!
public class Tuple {
	
	/*
	 * We endure this horribleness in order to provide a way to create classes that hold values of definite types (no more nasty casting)
	 * that may also be used in hashmaps, should the need arrive. I'm kinda tired of making new classes just to hold values for one single list.
	 */

	public static class Pair<X,Y> {

		X A;
		Y B;
		
		public Pair(X a, Y b) {
			this.A = a;
			this.B = b;
		}
		
		public X getA() {
			return this.A;
		}
		
		public Y getB() {
			return this.B;
		}

		public X getKey() {
			return this.A;
		}

		public Y getValue() {
			return this.B;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((A == null) ? 0 : A.hashCode());
			result = prime * result + ((B == null) ? 0 : B.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if(A == null) {
				if(other.A != null)
					return false;
			} else if(!A.equals(other.A))
				return false;
			if(B == null) {
				if(other.B != null)
					return false;
			} else if(!B.equals(other.B))
				return false;
			return true;
		}

		public void setA(X a) {
			this.A = a;
		}

		public void setB(Y b) {
			this.B = b;
		}
	}

	public static class Triplet<X,Y,Z> {

		X A;
		Y B;
		Z C;
		
		public Triplet(X a,Y b,Z c) {
			this.A = a;
			this.B = b;
			this.C = c;
		}
		
		public X getA() {
			return this.A;
		}
		
		public Y getB() {
			return this.B;
		}
		
		public Z getC() {
			return this.C;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((A == null) ? 0 : A.hashCode());
			result = prime * result + ((B == null) ? 0 : B.hashCode());
			result = prime * result + ((C == null) ? 0 : C.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Triplet other = (Triplet) obj;
			if(A == null) {
				if(other.A != null)
					return false;
			} else if(!A.equals(other.A))
				return false;
			if(B == null) {
				if(other.B != null)
					return false;
			} else if(!B.equals(other.B))
				return false;
			if(C == null) {
				if(other.C != null)
					return false;
			} else if(!C.equals(other.C))
				return false;
			return true;
		}

		public void setA(X a) {
			this.A = a;
		}

		public void setB(Y b) {
			this.B = b;
		}

		public void setC(Z c) {
			this.C = c;
		}
	}

	public static class Quartet<W,X,Y,Z> {

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((A == null) ? 0 : A.hashCode());
			result = prime * result + ((B == null) ? 0 : B.hashCode());
			result = prime * result + ((C == null) ? 0 : C.hashCode());
			result = prime * result + ((D == null) ? 0 : D.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Quartet other = (Quartet) obj;
			if(A == null) {
				if(other.A != null)
					return false;
			} else if(!A.equals(other.A))
				return false;
			if(B == null) {
				if(other.B != null)
					return false;
			} else if(!B.equals(other.B))
				return false;
			if(C == null) {
				if(other.C != null)
					return false;
			} else if(!C.equals(other.C))
				return false;
			if(D == null) {
				if(other.D != null)
					return false;
			} else if(!D.equals(other.D))
				return false;
			return true;
		}

		W A;
		X B;
		Y C;
		Z D;
		
		public Quartet(W a, X b, Y c, Z d) {
			this.A = a;
			this.B = b;
			this.C = c;
			this.D = d;
		}
		
		public W getA() {
			return this.A;
		}
		
		public X getB() {
			return this.B;
		}
		
		public Y getC() {
			return this.C;
		}
		
		public Z getD() {
			return this.D;
		}

		public void setA(W a) {
			this.A = a;
		}

		public void setB(X b) {
			this.B = b;
		}

		public void setC(Y c) {
			this.C = c;
		}

		public void setD(Z d) {
			this.D = d;
		}
	}
}