const PostForms = async (values, url) => {

  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',

    body: JSON.stringify(values),
  });

  return response;
}

export default PostForms;
