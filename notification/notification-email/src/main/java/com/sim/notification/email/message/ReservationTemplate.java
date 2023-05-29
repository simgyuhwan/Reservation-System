package com.sim.notification.email.message;

public class ReservationTemplate {

	public static String generateSubject(ReservationCompleteMessage reservationCompleteMessage) {
		return "공연 예약 완료 : ["+ reservationCompleteMessage.getPerformanceName() + "]";
	}

	public static String generateSubject(ReservationCancelMessage reservationCancelMessage) {
		return "공연 예약 취소 완료 : [" + reservationCancelMessage.getPerformanceName() + "}";
	}

	public static String generateBody(ReservationCompleteMessage reservationCompleteMessage) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><style>body {font-family: Arial, sans-serif; font-size: 14px;} h1 {color: #333333; font-size: 24px;} p {margin: 10px 0;} a {color: #0066cc; text-decoration: none;} a:hover {text-decoration: underline;}</style></head><body>")
			.append("<h1>공연 예약 완료 알림: ").append(reservationCompleteMessage.getPerformanceName()).append("</h1>")
			.append("<p>안녕하세요, <strong>").append(reservationCompleteMessage.getUsername()).append("</strong>님. <strong>").append(
				reservationCompleteMessage.getPerformanceName()).append("</strong> 공연에 대한 예약을 완료해주셔서 감사합니다.</p>")
			.append("<p>예약하신 공연 정보는 다음과 같습니다.</p>")
			.append("<ul>")
			.append("<li><strong>공연 날짜: ").append(reservationCompleteMessage.getStartDate()).append("</strong></li>")
			.append("<li><strong>공연 시간: ").append(reservationCompleteMessage.getStartTime()).append("</strong></li>")
			.append("<li><strong>공연 장소: ").append(reservationCompleteMessage.getPlace()).append("</strong></li>")
			.append("</ul>")
			.append("<p>만약 공연에 대한 문의가 있으시면 고객센터로 연락주시기 바랍니다.</p>")
			.append("<p>전화번호: 02-1234-5678</p>")
			.append("<p>이메일 주소: <a href=\"mailto:onlyTest@test.com\">support@reservation.service.com</a></p>")
			.append("<p>만약 공연 예약을 취소하시려면 <a href=\"https://reservation.service.com/cancel\">여기</a>를 클릭해주세요.</p>")
			.append("<p>예약해주셔서 다시 한 번 감사드리며, 즐거운 관람 되시길 바랍니다.</p>")
			.append("</body></html>");
		return sb.toString();
	}

	public static String generateBody(ReservationCancelMessage reservationCancelMessage) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><style>body {font-family: Arial, sans-serif; font-size: 14px;} h1 {color: #333333; font-size: 24px;} p {margin: 10px 0;} a {color: #0066cc; text-decoration: none;} a:hover {text-decoration: underline;}</style></head><body>")
			.append("<h1>공연 예약 취소 알림: ").append(reservationCancelMessage.getPerformanceName()).append("</h1>")
			.append("<p>안녕하세요, <strong>").append(reservationCancelMessage.getUsername()).append("</strong>님. <strong>").append(
				reservationCancelMessage.getPerformanceName()).append("</strong> 공연 예약이 취소되었습니다.</p>")
			.append("<p>취소된 공연 정보는 다음과 같습니다.</p>")
			.append("<ul>")
			.append("<li><strong>공연 날짜: ").append(reservationCancelMessage.getStartDate()).append("</strong></li>")
			.append("<li><strong>공연 시간: ").append(reservationCancelMessage.getStartTime()).append("</strong></li>")
			.append("<li><strong>공연 장소: ").append(reservationCancelMessage.getPlace()).append("</strong></li>")
			.append("</ul>")
			.append("<p>이메일 주소: <a href=\"mailto:onlyTest@test.com\">support@reservation.service.com</a></p>")
			.append("<p>다른 문의사항이 있으시면 고객센터로 연락주시기 바랍니다.</p>")
			.append("<p>전화번호: 02-1234-5678</p>")
			.append("<p>감사합니다.</p>")
			.append("</body></html>");
		return sb.toString();
	}
}
