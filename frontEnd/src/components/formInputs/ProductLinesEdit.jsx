import React, { useState, useRef, useEffect } from "react";
import { Box, Typography } from "@mui/material";

import { TextField } from "@mui/material";
import Divider from '@mui/material/Divider';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';

const ProductLinesFormInputEdit = ({ handleBlur, handleChange, values, touched,
  errors, setFieldValue, onResetImage, setImageChanged }) => {

  const [image, setImage] = useState(null);
  const [dragActive, setDragActive] = useState(false);
  const inputRef = useRef(null);

  const validateAndLoadFile = (file) => {
    if (!file) return;

    const reader = new FileReader();
    reader.onload = () => {
      setImage(reader.result);
    };
    reader.readAsDataURL(file);

    setFieldValue('image', file);
  };


  useEffect(() => {
    if (values.image instanceof File) {
      // Se já for um arquivo, apenas exibe o preview
      const reader = new FileReader();
      reader.onload = () => {
        setImage(reader.result);
      };
      reader.readAsDataURL(values.image);
    } else if (typeof values.image === "string" && values.image.length > 0) {
      const mimeType = values.image.startsWith("/9j/") ? "image/jpeg" : "image/png";
      setImage(`data:${mimeType};base64,${values.image}`);

      fetch(`data:${mimeType};base64,${values.image}`)
        .then(res => res.blob())
        .then(blob => {
          const extension = mimeType === "image/jpeg" ? "jpeg" : "png";
          const file = new File([blob], `image.${extension}`, { type: mimeType });
          setFieldValue("image", file, true);
        });
    } else {
      setImage(null);
      setFieldValue("image", null, true);
    }
  }, [values.image, setFieldValue]);


  const handleDragOver = (event) => {
    event.preventDefault();
    setDragActive(true);
  };

  const handleDragLeave = () => {
    setDragActive(false);
  };

  const handleDrop = (event) => {
    event.preventDefault();
    setDragActive(false);
    const file = event.dataTransfer.files[0];
    validateAndLoadFile(file);
  };

  const handleClick = () => {
    inputRef.current.click();
  };

  const handleFileChange = (event) => {
    const file = event.currentTarget.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onloadend = () => {
      setImage(reader.result);
      setFieldValue("image", file);
    };
    reader.readAsDataURL(file);
    setImageChanged(true);

  };

  return (
    <>
      <Divider sx={{ gridColumn: "span 5" }} />

      <TextField
        variant="filled"
        type="text"
        label="Product Line"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.productLine}
        name="productLine"
        error={!!touched.productLine && !!errors.productLine}
        helperText={touched.productLine && errors.productLine}
        sx={{ gridColumn: "span 1" }}
        disabled={true}
      />

      <TextField
        variant="filled"
        type="text"
        label="Text Description"
        multiline
        rows={5}
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.textDescription}
        name="textDescription"
        error={!!touched.textDescription && !!errors.textDescription}
        helperText={touched.textDescription && errors.textDescription}
        sx={{ gridColumn: "span 1" }}
      />

      <TextField
        variant="filled"
        type="text"
        label="Html Description"
        multiline
        rows={5}
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.htmlDescription}
        name="htmlDescription"
        error={!!touched.htmlDescription && !!errors.htmlDescription}
        helperText={touched.htmlDescription && errors.htmlDescription}
        sx={{ gridColumn: "span 1" }}
      />

      <Box>
        <Box
          role="button"
          aria-label="Upload an image"
          onDragOver={handleDragOver}
          onDragLeave={handleDragLeave}
          onDrop={handleDrop}
          onClick={handleClick}
          sx={{
            gridColumn: "span 2",
            width: 600,
            height: 400,
            border: '2px dashed #aaa',
            borderRadius: '8px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            cursor: 'pointer',
            backgroundColor: dragActive ? '#1f2a40' : 'transparent',
            backgroundImage: image ? `url(${image})` : 'none',
            backgroundSize: 'contain',
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            position: 'relative',
            overflow: 'hidden',
          }}
        >
          {!image && (
            <>
              <CloudUploadIcon
                sx={{
                  fontSize: 80,
                  color: '#aaa',
                  mb: 1,
                }}
              />
              <Typography color="textSecondary">
                {dragActive ? 'Drop image here' : 'Drag one image or click to select'}
              </Typography>
            </>
          )}
        </Box>
        <input
          type="file"
          name="image"
          // accept={validFileExtensions.map(ext => `image/${ext}`).join(',')}
          accept="image/png, image/jpeg"
          ref={inputRef}
          // onChange={(event) => {
          //   const file = event.currentTarget.files[0];
          //   validateAndLoadFile(file);
          // }}
          onChange={handleFileChange}
          style={{ display: 'none' }}
        />
        <div>
          {touched.image && errors.image && (
            <p style={{ color: 'red' }}>{errors.image}</p>
          )}
        </div>
      </Box>

    </>
  )

}

export default ProductLinesFormInputEdit;
