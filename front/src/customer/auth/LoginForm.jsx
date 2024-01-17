import { Button, Grid, TextField } from "@mui/material";
import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { login } from "../../State/Auth/Action";

const LoginForm = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  
  /*  
  const [showPassword, setShowPassword] = React.useState(false);
  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };
    */


  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const userData = {
      email: data.get("email"),
      password: data.get("password"),
    };
    dispatch(login(userData))
    navigate("/");

  };
  return (
    <div className="">
      <button
        className="bg-white w-1/2 absolute top-0 left-0 h-16 z-20 border-t-2 border-l-2 border-orange-500"
        style={{
          color: "#ff8c3c",
        }}
      >
        Giriş Yap
      </button>
      <button
        className="bg-gray-200 w-1/2 absolute top-0 right-0 h-16 z-20 border-b-2 border-l-2 border-orange-500border-b-2 border-l-2 border-orange-500"
        onClick={() => navigate("/register")}
      >
        Kayıt Ol
      </button>

      <div className="pt-[5rem]">
        <form onSubmit={handleSubmit}>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <TextField
                required
                id="email"
                name="email"
                label="E-Posta"
                fullWidth
                autoComplete="email"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                id="password"
                name="password"
                label="Şifre"
                fullWidth
                autoComplete="password"
                type="password"
              />
            </Grid>
            <Grid item xs={12}>
              <Button
                className="w-full"
                type="submit"
                variant="contained"
                size="large"
                sx={{ padding: ".8rem 0", bgcolor: "#F97316" }}
              >
                Giriş Yap
              </Button>
            </Grid>
          </Grid>
        </form>
        <div className="flex justify-center flex-col items-center">
          <div className="py-3 flex items-center">
            <p>Hesabınız yok mu?</p>
            <Button
              onClick={() => navigate("/register")}
              className="ml-5 "
              size="small"
              sx={{
                "&:hover": {
                  color: "#F97316",
                }
              }}
            >
              Kayıt Ol
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
