
const PostForms = async (values, url) => {
  const token = import.meta.env.VITE_TOKEN;

  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${token}`,
    },
    credentials: 'include',

    body: JSON.stringify(values),
  });

  return response;
}

export default PostForms;
