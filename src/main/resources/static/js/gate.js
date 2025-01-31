let guestbookEntries = [];
let currentPage = 1;
const entriesPerPage = 6;

// 페이지가 로드될 때 초기 데이터 로드
document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/guestbook/get')
        .then(response => response.json())
        .then(data => {
            guestbookEntries = data;
            renderGuestbook();
        });
});

// 방명록 추가
function addGuestbook() {
    const input = document.getElementById('guestbookInput');
    if (input.value.trim() !== '') {
        const entry = { content: input.value };

        fetch('/api/guestbook/post', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(entry)
        })
            .then(response => response.json())  // 응답을 JSON으로 변환
            .then(data => {
                guestbookEntries.unshift(data); // 새 방명록을 배열의 맨 앞에 추가
                input.value = '';
                renderGuestbook(); // 최신 순서로 다시 렌더링
            });
    }
}

// 방명록 렌더링
function renderGuestbook() {
    const container = document.getElementById('guestbookContainer');
    container.innerHTML = '';

    const start = (currentPage - 1) * entriesPerPage;
    const end = start + entriesPerPage;
    const pageEntries = guestbookEntries.slice(start, end);

    pageEntries.forEach(entry => {
        const div = document.createElement('div');
        div.className = 'guestbook-entry';
        div.textContent = entry.content;
        container.appendChild(div);
    });

    document.getElementById('pageNumber').textContent = currentPage;
}

// 이전 페이지
function prevPage() {
    if (currentPage > 1) {
        currentPage--;
        renderGuestbook();
    }
}

// 다음 페이지
function nextPage() {
    if (currentPage * entriesPerPage < guestbookEntries.length) {
        currentPage++;
        renderGuestbook();
    }
}