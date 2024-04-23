import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import CryptoJS from "crypto-js";

const useQueryCustom = (key, path, config = {}, params = null) => {
    const response = async () => {
        const data = await axios.get(
            `${import.meta.env.VITE_REACT_BACKEND_URL}/${path}`,
            params
        );

        if (data?.data?.ciphertext && data?.data?.hash) {
            const hash = CryptoJS.MD5(data.data.ciphertext).toString();
			console.warn(`${import.meta.env.VITE_CIPHER_KEY}`);
            if (hash === data.data.hash) {
                // Decrypt
                const decrypted = CryptoJS.DES.decrypt(
                    data.data.ciphertext,
                    `${import.meta.env.VITE_CIPHER_KEY}`,
                    {
                        mode: CryptoJS.mode.ECB, // Electronic Codebook mode
                        padding: CryptoJS.pad.Pkcs7, // Padding scheme
                    }
                ).toString(CryptoJS.enc.Utf8);

                return JSON.parse(decrypted);
            } else {
                return { error: "data had been manipulated" };
            }
        }

        return data;
    };

    return useQuery({
        queryKey: key,
        queryFn: response,
        ...config,
    });
};

export default useQueryCustom;
