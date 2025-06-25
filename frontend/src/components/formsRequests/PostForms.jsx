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

// import { useState, useCallback } from 'react';
// import { useRefreshtToken } from "../../auth/RefreshToken";
//
// const usePostForms = () => {
//   const refreshToken = useRefreshtToken();
//   const [err, setErr] = useState(null);
//
//   const fetchPost = useCallback(async (url, retry = false) => {
//     try {
//
//       const res = await fetch(url, {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         credentials: 'include',
//
//         body: JSON.stringify(values),
//       });
//
//       if (res.status === 403 && !retry) {
//         console.warn("Token. Attempting to refresh...");
//         await refreshToken();
//         return fetchPost(url, true);
//       }
//
//       if (!res.ok) {
//         throw new Error('Network response was not ok');
//       }
//
//       return res;
//
//     } catch (error) {
//       setErr(error);
//     }
//   }, [refreshToken]);
//
//   return { err, fetchPost };
// }
//
// export default usePostForms;
