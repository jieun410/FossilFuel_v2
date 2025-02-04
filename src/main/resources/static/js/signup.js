document.getElementById('send-code').addEventListener('click', async function() {
    const email = document.getElementById('email').value;

    if (!email) {
        alert("이메일을 입력해주세요.");
        return;
    }

    try {
        const response = await fetch('/api/send-verification-code', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ email })
        });

        if (response.ok) {
            alert("인증 코드가 이메일로 전송되었습니다.");
        } else {
            const errorText = await response.text();
            alert("인증 코드 전송 실패: " + errorText);
        }
    } catch (error) {
        console.error("인증 코드 전송 오류: ", error);
        alert("인증 코드 전송 중 문제가 발생했습니다.");
    }
});

document.getElementById('verify-code').addEventListener('click', async function () {
    const code = document.getElementById('code').value;

    if (!code) {
        alert("인증 코드를 입력해주세요.");
        return;
    }

    try {
        const response = await fetch('/api/verify-code', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({code})
        });

        if (response.ok) {
            alert("이메일 인증 성공!");
            // 이메일 인증이 완료되면 이메일 필드를 disabled로 설정
            document.getElementById('email').disabled = true; // 이메일 필드를 수정 불가능하게 만듦
        } else {
            const errorText = await response.text();
            alert("이메일 인증 실패: " + errorText);
        }
    } catch (error) {
        console.error("인증 코드 확인 오류: ", error);
        alert("인증 코드 확인 중 문제가 발생했습니다.");
    }
});


document.getElementById('signup').addEventListener('click', async function() {
    const nickname = document.getElementById('nickname').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const grade = document.getElementById('grade').value;

    if (!nickname || !email || !password || !confirmPassword || !grade) {
        alert("모든 필드를 입력해주세요.");
        return;
    }

    if (password !== confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    if (password.length < 6) {
        alert("비밀번호는 최소 6자 이상이어야 합니다.");
        return;
    }

    try {
        // 이메일 중복 체크 요청
        const emailCheckResponse = await fetch(`/api/members/check-email?email=${email}`, { method: 'GET' });

        if (!emailCheckResponse.ok) {
            const errorText = await emailCheckResponse.text();
            alert(errorText); // 중복된 이메일 메시지 표시
            return;
        }

        // 서버에서 이메일 인증 여부 확인
        const emailVerifyResponse = await fetch('/api/email-last-verified', { method: 'GET' });

        if (!emailVerifyResponse.ok) {
            alert("이메일 인증을 먼저 완료해주세요.");
            return;
        }

        const response = await fetch('/api/members/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nickname, email, password, grade })
        });

        if (response.ok) {
            alert("회원가입 성공!");
            window.location.href = '/login.html'; // 로그인 페이지로 이동
        } else {
            const errorText = await response.text();
            alert("회원가입 실패: " + errorText);
        }
    } catch (error) {
        console.error("회원가입 요청 오류: ", error);
        alert("회원가입 중 문제가 발생했습니다.");
    }
});