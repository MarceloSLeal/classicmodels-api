const DeleteScenes = async (url) => {

  const response = await fetch(url, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  })

  return response;
}

export default DeleteScenes;
