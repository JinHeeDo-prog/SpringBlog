const deleteButton = document.getElementById("delete-btn");
if(deleteButton) {
    deleteButton.addEventListener('click', event => {
        console.log("wsdinof");
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        }).then(() => {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        });
    });
}
console.log(deleteButton);
const modifyButton = document.getElementById('modify-btn');

if(modifyButton) {
    modifyButton.addEventListener('click', event => {

        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('contents').value
            })
        }).then(() => {
            alert('수정이 완료되었습니다.');
            location.replace(`/articles`);
        });
    });
}

const createButton = document.getElementById("create-btn");
    if(createButton){
        createButton.addEventListener('click',event => {
          fetch("/api/articles",{
              method:'POST',
              headers: {
                  'Content-Type': 'application/json'
              },
              body : JSON.stringify({
                  title:  document.getElementById("title").value,
                  content: document.getElementById("contents").value
              })
          }).then(()=>{
              alert("등록되었습니다.");
              location.replace('/articles');
          })
        })
    }