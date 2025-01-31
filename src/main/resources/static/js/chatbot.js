document.getElementById('chat-form').addEventListener('submit', async (event) => {
    event.preventDefault(); // 폼 제출 방지
    const message = document.getElementById('message').value.trim();
    const chatBox = document.getElementById('chat-box');

    if (!message) return; // 빈 메시지 방지

    // 🟦 사용자가 입력한 메시지 추가
    addMessage(message, 'user-message');
    document.getElementById('message').value = '';

    // ⏳ Typing Indicator 추가
    const typingIndicator = document.createElement('div');
    typingIndicator.classList.add('chat-message', 'bot-message', 'typing-indicator');
    typingIndicator.innerHTML = '<span></span><span></span><span></span>';
    chatBox.appendChild(typingIndicator);
    chatBox.scrollTop = chatBox.scrollHeight;

    // 🎯 서버에 메시지 전송
    try {
        const response = await fetch('/api/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ message })
        });

        const result = await response.json();

        // ⏳ Typing Indicator 제거 후 AI 응답 추가
        chatBox.removeChild(typingIndicator);
        addMessage(result.reply || '응답을 받을 수 없습니다.', 'bot-message');
    } catch (error) {
        chatBox.removeChild(typingIndicator);
        addMessage('⚠️ 에러 발생. 다시 시도해주세요.', 'bot-message');
    }
});

// ✅ 메시지 추가 함수 (애니메이션 효과 포함)
function addMessage(text, className) {
    const chatBox = document.getElementById('chat-box');
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('chat-message', className);
    messageDiv.innerHTML = `<p>${text}</p>`;
    chatBox.appendChild(messageDiv);

    // ✨ 부드러운 메시지 표시 애니메이션
    setTimeout(() => {
        messageDiv.style.opacity = '1';
        messageDiv.style.transform = 'translateY(0)';
    }, 10);

    chatBox.scrollTop = chatBox.scrollHeight;
}