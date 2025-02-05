document.getElementById("findIdForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const nickname = document.getElementById("nickname").value;

    fetch('/api/auth/find-id', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nickname })
    })
        .then(response => response.json())
        .then(data => {
            const messageDiv = document.getElementById("result-message");
            if (data.success) {  // 백엔드에서 success 필드를 반환하므로 이 부분이 정상 작동
                messageDiv.style.color = "green";
                messageDiv.textContent = "등록된 이메일: " + data.email;
            } else {
                messageDiv.style.color = "red";
                messageDiv.textContent = data.message;  // 서버에서 제공하는 메시지 출력
            }
        })
        .catch(error => {
            console.error("에러 발생:", error);
            document.getElementById("result-message").textContent = "서버 오류가 발생했습니다.";
        });
});